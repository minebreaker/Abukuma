package rip.deadcode.abukuma3.request;

import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.internal.Unsafe;

import javax.servlet.http.HttpServletRequest;

public final class AbuRequestHeader {

    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;

    public AbuRequestHeader( Request jettyRequest, HttpServletRequest servletRequest ) {
        this.jettyRequest = jettyRequest;
        this.servletRequest = servletRequest;
    }

    @Unsafe
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    @Unsafe
    public Request getJettyRequest() {
        return jettyRequest;
    }
}
