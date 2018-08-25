package rip.deadcode.abukuma3.request;

import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;
import rip.deadcode.abukuma3.parser.AbuParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.akashi.util.Uncheck.tryUncheck;

public final class AbuRequest {

    private final ExecutionContext context;
    private final AbuRequestHeader header;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;
    private final HttpServletResponse servletResponse;

    public AbuRequest(
            ExecutionContext context,
            AbuRequestHeader header,
            Request jettyRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) {
        this.context = context;
        this.header = header;
        this.jettyRequest = jettyRequest;
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
    }

    @SuppressWarnings( "unchecked" )
    public <T> T getBody( Class<T> cls ) {
        AbuParser<?> parser = context.getParsers().get( cls );
        checkNotNull( parser, "Could not find an appropriate parser for the type '%s'.", cls );
        return (T) parser.parse( tryUncheck( () -> jettyRequest.getInputStream() ), header );
    }

    public ExecutionContext getContext() {
        return context;
    }

    public AbuRequestHeader getHeader() {
        return header;
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
