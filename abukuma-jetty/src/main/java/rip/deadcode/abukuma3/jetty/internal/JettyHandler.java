package rip.deadcode.abukuma3.jetty.internal;

import com.google.common.net.HttpHeaders;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.jetty.internal.value.JettyRequest;
import rip.deadcode.abukuma3.jetty.internal.value.JettyRequestHeader;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.internal.SerializeCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public final class JettyHandler extends AbstractHandler {

    private final HandlerAdapter<Request, HttpServletResponse> handlerAdapter;

    JettyHandler( AbuExecutionContext context ) {

        this.handlerAdapter = new HandlerAdapter<Request, HttpServletResponse>( context ) {

            @Override
            public AbuRequestHeader createHeader( AbuExecutionContext context, Request originalRequest ) {
                return new JettyRequestHeader( context, originalRequest );
            }

            @Override
            public AbuRequest createRequest(
                    AbuExecutionContext context,
                    AbuRequestHeader header,
                    Request originalRequest,
                    HttpServletResponse originalResponse,
                    Map<String, String> pathParams ) {
                return new JettyRequest(
                        context,
                        header,
                        originalRequest,
                        originalResponse,
                        pathParams
                );
            }

            @Override
            public void submitResponse(
                    AbuExecutionContext context,
                    AbuResponse response,
                    AbuRenderingResult renderingResult,
                    Request originalRequest,
                    HttpServletResponse originalResponse ) throws Exception {

                originalResponse.setStatus( response.status() );
                response.header().forEach( ( k, v ) -> {
                    // TODO duplicate header check
                    originalResponse.setHeader( k, v );
                } );
                // TODO duplicate cookie check
                response.cookie().forEach( c -> {
                    // We don't use ServletResponse.addCookie() because it doesn't support SameSite.
                    originalResponse.setHeader( HttpHeaders.SET_COOKIE, SerializeCookie.serialize( c ) );
                } );

                renderingResult.rendering().accept( originalResponse.getOutputStream() );

                originalRequest.setHandled( true );
            }
        };
    }

    @Override
    public void handle(
            String target,
            Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) {

        handlerAdapter.handle( baseRequest, servletResponse );
    }
}
