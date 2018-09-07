package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.router.RoutingContext;

import java.util.Map;

public final class RoutingContextImpl implements RoutingContext {

    private final Map<String, String> pathParams;
    private final AbuHandler handler;

    public RoutingContextImpl( Map<String, String> pathParams, AbuHandler handler ) {
        this.pathParams = pathParams;
        this.handler = handler;
    }

    @Override public Map<String, String> getPathParams() {
        return pathParams;
    }

    @Override public AbuHandler getHandler() {
        return handler;
    }
}
