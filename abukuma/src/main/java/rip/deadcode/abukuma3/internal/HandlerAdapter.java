package rip.deadcode.abukuma3.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.internal.RoutingContextImpl;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


public abstract class HandlerAdapter<Q, R> {

    private static final Logger logger = LoggerFactory.getLogger( HandlerAdapter.class );

    private final AbuExecutionContext context;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;
    private final AbuRenderer renderer;
    private final AbuFilter filter;

    protected HandlerAdapter( AbuExecutionContext context ) {
        this.context = context;
        this.router = context.router();
        this.exceptionHandler = context.exceptionHandler();
        this.renderer = context.renderer();
        this.filter = context.filter();
    }

    protected abstract AbuRequestHeader createHeader( AbuExecutionContext context, Q originalRequest );

    protected abstract AbuRequest createRequest(
            AbuExecutionContext context,
            AbuRequestHeader header,
            Q originalRequest,
            R originalResponse,
            Map<String, String> pathParams );

    protected abstract void submitResponse(
            AbuExecutionContext context,
            AbuResponse response,
            AbuRenderingResult renderingResult,
            Q originalRequest,
            R originalResponse ) throws Exception;

    public void handle( Q originalRequest, R originalResponse ) {

        AbuRequestHeader header = createHeader( context, originalRequest );
        RoutingResult route = router.route( new RoutingContextImpl(
                header,
                header.urlString(),
                header.urlString()
        ) );
        checkNotNull( route, "No matching route found." );

        AbuRequest request = createRequest(
                context,
                header,
                originalRequest,
                originalResponse,
                route.parameters()
        );

        AbuHandler handler = route.handler();

        AbuResponse response = possibly(
                () -> filter.filter( request, handler )
        ).orElse(
                e -> exceptionHandler.handleException( e, request )
        );

        try {
            AbuRenderingResult renderingResult = renderer.render( context, response );
            checkNotNull( renderingResult );
            AbuResponse renderedResponse = renderingResult.modifying().get();

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
