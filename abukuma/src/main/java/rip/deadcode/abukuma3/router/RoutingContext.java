package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.RequestHeader;


public interface RoutingContext {

    public RequestHeader header();

    public ExecutionContext executionContext();
}
