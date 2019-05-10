package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.router.RoutingResult;

import java.util.Map;


public class RoutingResultImpl implements RoutingResult {

    private final AbuHandler handler;
    private final Map<String, String> parameters;

    public RoutingResultImpl( AbuHandler handler, Map<String, String> parameters ) {
        this.handler = handler;
        this.parameters = parameters;
    }

    @Override public AbuHandler handler() {
        return handler;
    }

    @Override public Map<String, String> parameters() {
        return parameters;
    }
}
