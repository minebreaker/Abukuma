package rip.deadcode.abukuma3;

public final class DefaultExceptionHandler implements AbuExceptionHandler {

    @Override public AbuResponse handleException( Exception e ) {
        throw new RuntimeException();  // TODO
    }
}
