package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

public final class DefaultExceptionHandler implements AbuExceptionHandler {

    @Override public AbuResponse handleException( Exception e, AbuRequest request ) {
        throw new RuntimeException();  // TODO
    }
}
