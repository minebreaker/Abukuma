package rip.deadcode.abukuma3;

public final class DefaultExceptionHandler implements AExceptionHandler {

    @Override public AResponse handleException( Exception e ) {
        throw new RuntimeException();  // TODO
    }
}
