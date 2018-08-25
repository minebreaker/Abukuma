package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.request.AbuRequest;
import rip.deadcode.abukuma3.request.AbuRequestHeader;
import rip.deadcode.abukuma3.response.AbuResponse;
import rip.deadcode.abukuma3.router.AbuRouter;

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
        this.renderer = context.getRenderers().stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get();
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) throws IOException, ServletException {

        AbuRequestHeader header = new AbuRequestHeader( context, baseRequest, servletRequest );
        AbuHandler handler = router.route( header );
        AbuRequest request = new AbuRequest( context, header, baseRequest, servletRequest, servletResponse );

        AbuResponse response;
        try {
            response = handler.handle( request );

        } catch ( Exception e ) {
            response = exceptionHandler.handleException( e, request );
        }

        renderer.render( servletResponse.getOutputStream(), response.getBody() );

        baseRequest.setHandled( true );
    }
}
