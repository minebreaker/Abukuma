package rip.deadcode.abukuma3.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.internal.RoutingContextImpl;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Response;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


public abstract class HandlerAdapter<Q, R> {

    private static final Logger logger = LoggerFactory.getLogger( HandlerAdapter.class );

    private final ExecutionContext context;
    private final Router router;
    private final ExceptionHandler exceptionHandler;
    private final Renderer renderer;
    private final Filter filter;

    protected HandlerAdapter( ExecutionContext context ) {
        this.context = context;
        this.router = context.router();
        this.exceptionHandler = context.exceptionHandler();
        this.renderer = context.renderer();
        this.filter = context.filter();
    }

    protected abstract RequestHeader createHeader( ExecutionContext context, Q originalRequest );

    protected abstract Request createRequest(
            RequestHeader header,
            Q originalRequest,
            R originalResponse,
            Map<String, String> pathParams );

    protected abstract void submitResponse(
            ExecutionContext context,
            Response response,
            RenderingResult renderingResult,
            Q originalRequest,
            R originalResponse ) throws Exception;

    public void handle( Q originalRequest, R originalResponse ) {

        RequestHeader header = createHeader( context, originalRequest );
        RoutingResult route = router.route( new RoutingContextImpl(
                header,
                header.urlString(),
                header.urlString()
        ) );
        checkNotNull( route, "No matching route found." );

        Request request = createRequest(
                header,
                originalRequest,
                originalResponse,
                route.parameters()
        );

        Handler handler = route.handler();

        Response response = possibly(
                () -> filter.filter( context, request, handler )
        ).orElse(
                e -> exceptionHandler.handleException( e, context, request )
        );

        try {
            RenderingResult renderingResult = renderer.render( context, response );
            checkNotNull( renderingResult );
            Response renderedResponse = renderingResult.modifying().get();

            submitResponse( context, renderedResponse, renderingResult, originalRequest, originalResponse );

        } catch ( Exception e ) {
            throw new RuntimeException( e );

        } finally {
            Object body = response.body();
            if ( body instanceof Closeable ) {
                try {
                    ( (Closeable) body ).close();
                } catch ( IOException e ) {
                    logger.warn( "Failed to close the response stream.", e );
                }
            }
        }
    }
}
