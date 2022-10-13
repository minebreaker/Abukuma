package rip.deadcode.abukuma3.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.internal.RoutingContextImpl;
import rip.deadcode.abukuma3.utils.url.UrlPathParseResult;
import rip.deadcode.abukuma3.utils.url.UrlPathParser;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapMap;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


// TODO: rewrite in functional manner
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

    /**
     * Retrieve a path from the request.
     * The path should not include query parameters.
     */
    protected abstract String pathString( Q originalRequest );

    protected abstract RequestHeader createHeader(
            ExecutionContext context,
            PersistentList<String> urlPaths,
            Q originalRequest );

    protected abstract <T> Request<T> createRequest(
            Function<InputStream, T> body,
            RequestHeader header,
            Q originalRequest,
            R originalResponse,
            PersistentMap<String, String> pathParams );

    protected abstract void submitResponse(
            ExecutionContext context,
            Response response,
            RenderingResult renderingResult,
            Q originalRequest,
            R originalResponse ) throws Exception;

    public void handle( Q originalRequest, R originalResponse ) {

        UrlPathParser urlPathParser = checkNotNull( context.get( UrlPathParser.class ) );
        UrlPathParseResult result = urlPathParser.parse( pathString( originalRequest ) );
        if ( !( result instanceof UrlPathParseResult.Success ) ) {
            // FIXME
            throw ( (UrlPathParseResult.Error) result ).validationErrors().last();
        }
        PersistentList<String> urlPaths = ( (UrlPathParseResult.Success) result ).result();

        RequestHeader header = createHeader( context, urlPaths, originalRequest );

        @Nullable RoutingResult route = router.route( new RoutingContextImpl(
                header,
                this.context
        ) );

        @SuppressWarnings( "unchecked" )
        Handler<Object> handler = route != null ? (Handler<Object>) route.handler()
                                                : (Handler<Object>) router.notFound();
        checkNotNull( handler, "No matching route found and not-found-router is not set." );
        Class<?> cls = handler.bodyType();
        Request<?> request = createRequest(
                is -> {
                    try {
                        Object body = context.parser().parse( cls, is, header );
                        checkNotNull( body, "Could not find an appropriate parser for the type '%s'.", cls );
                        checkState(
                                cls.isInstance( body ),
                                "Illegal instance '%s' of type '%s' was returned by the parser for the request '%s'. This may be caused by a bug of the parsers.",
                                body,
                                body.getClass(),
                                cls
                        );
                        return body;
                    } catch ( IOException e ) {
                        throw new RuntimeException( e );
                    }
                },
                header,
                originalRequest,
                originalResponse,
                route != null ? wrapMap( route.pathParameters() )
                              : createMap()
        );

        var beforeFilterResult = filter.filterBefore( context, request );
        Response response = possibly( () -> {
            if ( beforeFilterResult == null ) {
                return handler.handle( context, request );
            } else if ( beforeFilterResult instanceof Filter.SuccessfulBeforeFilterResult ) {
                var r = ( (Filter.SuccessfulBeforeFilterResult) result );
                return handler.handle( context, r.request() );
            } else if ( beforeFilterResult instanceof Filter.InterruptedBeforeFilterResult ) {
                var r = ( (Filter.InterruptedBeforeFilterResult) result );
                return r.response();
            } else {
                throw new RuntimeException( String.format(
                        "The class is neither SuccessfulBeforeFilterResult nor InterruptedBeforeFilterResult: %s",
                        result.getClass().getCanonicalName()
                ) );
            }
        } ).orElse(
                e -> exceptionHandler.handleException( e, context, request )
        );

        try {
            RenderingResult renderingResult = renderer.render( context, response );
            checkNotNull( renderingResult ); // FIXME
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
