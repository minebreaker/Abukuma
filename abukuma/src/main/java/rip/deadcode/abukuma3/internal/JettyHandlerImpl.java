package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.AbuRoutingContext;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.internal.AbuRequestImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


public final class JettyHandlerImpl extends AbstractHandler {

    private final AbuExecutionContext context;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;
    private final AbuRenderer renderer;
    private final AbuFilter filter;

    JettyHandlerImpl( AbuExecutionContext context ) {
        this.context = context;
        this.router = context.router();
        this.exceptionHandler = context.exceptionHandler();
        this.renderer = context.renderer();
        this.filter = context.filter();
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) throws IOException, ServletException {

        AbuRequestHeader header = new AbuRequestHeader( context, baseRequest );
        AbuRoutingContext routing = router.route( header );
        AbuRequest request = new AbuRequestImpl(
                context,
                header,
                baseRequest,
                servletRequest,
                servletResponse,
                routing.getPathParams()
        );

        AbuHandler handler = routing.getHandler();

        AbuResponse response = possibly(
                () -> filter.filter( request, handler )
        ).orElse(
                e -> exceptionHandler.handleException( e, request )
        );

        try {
            AbuRenderingResult renderingResult = renderer.render( context, response );
            checkNotNull( renderingResult );
            AbuResponse renderedResponse = renderingResult.modifying().get();

            servletResponse.setStatus( renderedResponse.status() );
            renderedResponse.header().forEach( ( k, v ) -> {
                servletResponse.setHeader( k, v );
            } );

            renderingResult.rendering().accept( servletResponse.getOutputStream() );

            baseRequest.setHandled( true );

        } catch ( Exception e ) {
            throw new RuntimeException( e );

        } finally {
            Object body = response.body();
            if ( body instanceof Closeable ) {
                ( (Closeable) body ).close();
            }
        }
    }
}
