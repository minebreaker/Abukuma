package rip.deadcode.abukuma3.request;

import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;

import javax.servlet.http.HttpServletRequest;

public final class AbuRequestHeader {

    private final ExecutionContext context;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;

    public AbuRequestHeader( ExecutionContext context, Request jettyRequest, HttpServletRequest servletRequest ) {
        this.context = context;
        this.jettyRequest = jettyRequest;
        this.servletRequest = servletRequest;
    }

    public ExecutionContext getContext() {
        return context;
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
