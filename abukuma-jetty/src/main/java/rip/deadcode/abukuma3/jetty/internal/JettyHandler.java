package rip.deadcode.abukuma3.jetty.internal;

import com.google.common.net.HttpHeaders;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.AbstractHandler;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.jetty.internal.value.JettyRequest;
import rip.deadcode.abukuma3.jetty.internal.value.JettyRequestHeader;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.internal.SerializeCookie;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;


public final class JettyHandler extends AbstractHandler {

    private final HandlerAdapter<org.eclipse.jetty.server.Request, HttpServletResponse> handlerAdapter;

    JettyHandler( ExecutionContext context ) {

        this.handlerAdapter = new HandlerAdapter<>( context ) {

            @Override protected String pathString( org.eclipse.jetty.server.Request originalRequest ) {
                return originalRequest.getRequestURI();
            }

            @Override
            public RequestHeader createHeader(
                    ExecutionContext context,
                    PersistentList<String> urlPaths,
                    org.eclipse.jetty.server.Request originalRequest ) {
                return new JettyRequestHeader( context, urlPaths, originalRequest );
            }

            @Override
            public <T> Request<T> createRequest(
                    Function<InputStream, T> is2body,
                    RequestHeader header,
                    org.eclipse.jetty.server.Request originalRequest,
                    HttpServletResponse originalResponse,
                    PersistentMap<String, String> pathParams ) {

                try (InputStream is = originalRequest.getInputStream()) {
                    return new JettyRequest<>(
                            is2body.apply( is ),
                            header,
                            originalRequest,
                            originalResponse,
                            pathParams
                    );
                } catch ( IOException e ) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void submitResponse(
                    ExecutionContext context,
                    Response response,
                    RenderingResult renderingResult,
                    org.eclipse.jetty.server.Request originalRequest,
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
            org.eclipse.jetty.server.Request baseRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) {

        handlerAdapter.handle( baseRequest, servletResponse );
    }
}
