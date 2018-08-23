package rip.deadcode.abukuma3;

import com.google.common.collect.ImmutableList;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public final class HandlerImpl extends AbstractHandler {

    private final ARouter router;
    private final AExceptionHandler exceptionHandler;

    HandlerImpl( ARouter router, AExceptionHandler exceptionHandler ) {
        this.router = router;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) throws IOException, ServletException {

        AResponse response;
        try {
            ARequestHeader header = new ARequestHeader( baseRequest, servletRequest );
            AHandler handler = router.route( header );
            ARequest request = new ARequest( header, baseRequest, servletRequest, servletResponse );
            response = handler.handle( request );

        } catch ( Exception e ) {
            response = exceptionHandler.handleException( e );
        }

        List<ARenderer> renderers = ImmutableList.of( new StringRenderer(), new InputStreamRenderer() );
        ARenderer renderer = renderers.stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get();
        renderer.render( servletResponse.getOutputStream(), response.getBody() );

        baseRequest.setHandled( true );
    }
}
