package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.AbuRoutingContext;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;

import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


public final class JettyHandlerImpl extends AbstractHandler {

    private final AbuExecutionContext context;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;
    private final AbuRenderer renderer;

    JettyHandlerImpl( AbuExecutionContext context ) {
        this.context = context;
        this.router = context.router();
        this.exceptionHandler = context.exceptionHandler();
        //noinspection OptionalGetWithoutIsPresent  // At least has default implementations
        this.renderer = context.renderers().stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get();
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) throws IOException, ServletException {

        AbuRequestHeader header = new AbuRequestHeader( context, baseRequest );
        AbuRoutingContext routing = router.route( header );
        AbuRequest request = new AbuRequest(
                context,
                header,
                baseRequest,
                servletRequest,
                servletResponse,
                routing.getPathParams()
        );

        AbuResponse response = possibly(
                () -> routing.getHandler().handle( request )
        ).orElse(
                e -> exceptionHandler.handleException( e, request )
        );


        try {
            servletResponse.setStatus( response.status() );
            response.header().forEach( ( k, v ) -> {
                servletResponse.setHeader( k, v );
            } );

            renderer.render( servletResponse.getOutputStream(), response.body() );

            baseRequest.setHandled( true );

        } finally {
            Object body = response.body();
            if ( body instanceof Closeable ) {
                ( (Closeable) body ).close();
            }
        }
    }
}
