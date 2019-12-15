package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;


@FunctionalInterface
public interface AbuHandler {
    public AbuResponse handle( AbuExecutionContext context, AbuRequest request );
}
