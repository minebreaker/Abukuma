package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;
import java.util.Map;


public final class RoutingResultImpl implements RoutingResult {

    private final Handler handler;
    @Nullable private final Map<String, String> pathParams;

    public RoutingResultImpl( Handler handler, @Nullable Map<String, String> pathParams ) {
        this.handler = handler;
        this.pathParams = pathParams;
    }

    @Override public Handler handler() {
        return handler;
    }

    @Override public Map<String, String> pathParameters() {
        return pathParams;
    }
}
