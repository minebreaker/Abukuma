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
        try {
            AbuRequestHeader header = new AbuRequestHeader( baseRequest, servletRequest );
            AbuHandler handler = router.route( header );
            AbuRequest request = new AbuRequest( header, baseRequest, servletRequest, servletResponse );
            response = handler.handle( request );

        } catch ( Exception e ) {
            response = exceptionHandler.handleException( e );
        }

        List<AbuRenderer> renderers = ImmutableList.of( new StringRenderer(), new InputStreamRenderer() );
        AbuRenderer renderer = renderers.stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get();
        renderer.render( servletResponse.getOutputStream(), response.getBody() );

        baseRequest.setHandled( true );
    }
}
