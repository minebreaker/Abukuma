package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.RoutingContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        AbuResponse response;
        try {
            response = routing.getHandler().handle( request );

        } catch ( Exception e ) {
            response = exceptionHandler.handleException( e, request );
        }

        servletResponse.setContentType( response.header().contentType() );

        renderer.render( servletResponse.getOutputStream(), response.body() );

        baseRequest.setHandled( true );
    }
}
