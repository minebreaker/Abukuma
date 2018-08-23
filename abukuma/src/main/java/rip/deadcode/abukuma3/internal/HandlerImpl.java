package rip.deadcode.abukuma3.internal;

import com.google.common.collect.ImmutableList;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.renderer.internal.StringRenderer;
import rip.deadcode.abukuma3.request.AbuRequest;
import rip.deadcode.abukuma3.request.AbuRequestHeader;
import rip.deadcode.abukuma3.response.AbuResponse;
import rip.deadcode.abukuma3.router.AbuRouter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public final class HandlerImpl extends AbstractHandler {

    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;

    HandlerImpl( AbuRouter router, AbuExceptionHandler exceptionHandler ) {
        this.router = router;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) throws IOException, ServletException {

        AbuResponse response;
        AbuRequestHeader header = new AbuRequestHeader( baseRequest, servletRequest );
        AbuHandler handler = router.route( header );
        AbuRequest request = new AbuRequest( header, baseRequest, servletRequest, servletResponse );
        try {
            response = handler.handle( request );

        } catch ( Exception e ) {
            response = exceptionHandler.handleException( e, request );
        }

        List<AbuRenderer> renderers = ImmutableList.of( new StringRenderer(), new InputStreamRenderer() );
        AbuRenderer renderer = renderers.stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get();
        renderer.render( servletResponse.getOutputStream(), response.getBody() );

        baseRequest.setHandled( true );
    }
}
