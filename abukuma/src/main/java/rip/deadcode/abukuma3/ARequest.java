package rip.deadcode.abukuma3;

import com.google.common.collect.ImmutableMap;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static rip.deadcode.akashi.util.Uncheck.tryUncheck;

public final class ARequest {

    private final Map<Class<?>, AParser<?>> converters = ImmutableMap.of();  // FIXME
    private final ARequestHeader header;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;
    private final HttpServletResponse servletResponse;

    ARequest(
            ARequestHeader header,
            Request jettyRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) {
        this.header = header;
        this.jettyRequest = jettyRequest;
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
    }

    @SuppressWarnings( "unchecked" )
    public <T> T getBody( Class<T> cls ) {
        return (T) converters.get( cls ).parse( tryUncheck( () -> jettyRequest.getInputStream() ), header );
    }

    @Unsafe
    public Request getJettyRequest() {
        return jettyRequest;
    }

    @Unsafe
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    @Unsafe
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }
}
