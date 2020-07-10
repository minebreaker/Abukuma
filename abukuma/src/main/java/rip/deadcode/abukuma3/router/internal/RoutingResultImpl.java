package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.RoutingResult;

import java.util.Map;


public class RoutingResultImpl implements RoutingResult {

    private final Handler handler;
    private final Map<String, String> parameters;

    public RoutingResultImpl( Handler handler, Map<String, String> parameters ) {
        this.handler = handler;
        this.parameters = parameters;
    }

    @Override public Handler handler() {
        return handler;
    }

    @Override public Map<String, String> parameters() {
        return parameters;
    }
}
