package rip.deadcode.abukuma3.jetty.internal;

import com.google.common.net.HttpHeaders;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.jetty.internal.value.JettyRequest;
import rip.deadcode.abukuma3.jetty.internal.value.JettyRequestHeader;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.AbuRoutingContext;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.internal.CookieImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


public final class JettyHandler extends AbstractHandler {

    private final AbuExecutionContext context;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;
    private final AbuRenderer renderer;
    private final AbuFilter filter;

    JettyHandler( AbuExecutionContext context ) {
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
            HttpServletResponse servletResponse ) throws IOException {

        AbuRequestHeader header = new JettyRequestHeader( context, baseRequest );
        AbuRoutingContext routing = router.route( header );
        AbuRequest request = new JettyRequest(
                context,
                header,
                baseRequest,
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
                // TODO duplicate header check
                servletResponse.setHeader( k, v );
            } );
            // TODO duplicate cookie check
            renderedResponse.cookie().forEach( c -> {
                // We don't use ServletResponse.addCookie() because it doesn't support SameSite.
                servletResponse.setHeader( HttpHeaders.SET_COOKIE, CookieImpl.serialize( c ) );
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
