package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.response.AbuResponse;

public final class DefaultExceptionHandler implements AbuExceptionHandler {

    @Override public AbuResponse handleException( Exception e ) {
        throw new RuntimeException();  // TODO
    }
}
