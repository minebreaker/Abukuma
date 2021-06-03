package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.value.RequestHeader;


public final class RoutingContextImpl implements RoutingContext {

    private final RequestHeader header;
    private final ExecutionContext executionContext;

    public RoutingContextImpl( RequestHeader header, ExecutionContext executionContext ) {
        this.header = header;
        this.executionContext = executionContext;
    }

    @Override public RequestHeader header() {
        return header;
    }

    @Override public ExecutionContext executionContext() {
        return executionContext;
    }
}
