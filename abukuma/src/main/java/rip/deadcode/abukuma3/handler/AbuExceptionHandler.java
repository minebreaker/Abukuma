package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

@FunctionalInterface
public interface AbuExceptionHandler {
    public AbuResponse handleException( Exception e, AbuRequest request );
}
