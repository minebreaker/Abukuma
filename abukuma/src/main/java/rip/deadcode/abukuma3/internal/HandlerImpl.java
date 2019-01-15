package rip.deadcode.abukuma3.internal;

import com.google.common.net.HttpHeaders;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;

import static rip.deadcode.akashi.util.Try.possibly;

public final class HandlerImpl extends AbstractHandler {

    private final ExecutionContext context;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;
    private final AbuRenderer renderer;

    HandlerImpl( ExecutionContext context ) {
        this.context = context;
        this.router = context.getRouter();
        this.exceptionHandler = context.getExceptionHandler();
        //noinspection OptionalGetWithoutIsPresent  // At least has default implementations
        this.renderer = context.getRenderers().stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get();
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) throws IOException, ServletException {

        AbuRequestHeader header = new AbuRequestHeader( context, baseRequest, servletRequest );
        RoutingContext routing = router.route( header );
        AbuRequest request = new AbuRequest( context, header, baseRequest, servletRequest, servletResponse, routing.getPathParams() );

        AbuResponse response = possibly(
                () -> routing.getHandler().handle( request )
        ).orElse(
                e -> exceptionHandler.handleException( e, request )
        );

        try {
            response.header().mayGet( HttpHeaders.CONTENT_TYPE ).ifPresent(
                    s -> servletResponse.setContentType( s )
            );

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
