package rip.deadcode.abukuma3.value;

import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

import static rip.deadcode.akashi.util.Uncheck.uncheck;

public final class AbuRequestHeader {

    private final ExecutionContext context;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;

    public AbuRequestHeader( ExecutionContext context, Request jettyRequest, HttpServletRequest servletRequest ) {
        this.context = context;
        this.jettyRequest = jettyRequest;
        this.servletRequest = servletRequest;
    }

    public ExecutionContext context() {
        return context;
    }

    public String method() {
        return jettyRequest.getMethod();
    }

    /**
     * Returns the URL reconstructed by jetty.
     *
     * @return URL of the request
     * @see Request#getRequestURL()
     */
    public URL url() {
        return uncheck( () -> new URL( jettyRequest.getRequestURL().toString() ) );
    }

    /**
     * Returns the URL {@link String} sent from the client.
     * It is without query parameters.
     *
     * @return URL String
     * @see Request#getRequestURI()
     */
    public String requestUrl() {
        return jettyRequest.getRequestURI();
    }

    @Unsafe
    public HttpServletRequest servletRequest() {
        return servletRequest;
    }

    @Unsafe
    public Request jettyRequest() {
        return jettyRequest;
    }
}
