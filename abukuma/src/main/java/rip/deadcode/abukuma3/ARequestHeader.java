package rip.deadcode.abukuma3;

import org.eclipse.jetty.server.Request;

import javax.servlet.http.HttpServletRequest;

public final class ARequestHeader {

    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;

    ARequestHeader( Request jettyRequest, HttpServletRequest servletRequest ) {
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
