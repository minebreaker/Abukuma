package rip.deadcode.abukuma3;

@FunctionalInterface
public interface AExceptionHandler {
    public AResponse handleException( Exception e );
}
