package rip.deadcode.abukuma3.request;

import com.google.common.collect.ImmutableMap;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.internal.Unsafe;
import rip.deadcode.abukuma3.parser.AbuParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static rip.deadcode.akashi.util.Uncheck.tryUncheck;

public final class AbuRequest {

    private final Map<Class<?>, AbuParser<?>> converters = ImmutableMap.of();  // FIXME
    private final AbuRequestHeader header;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;
    private final HttpServletResponse servletResponse;

    public AbuRequest(
            AbuRequestHeader header,
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
