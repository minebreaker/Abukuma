package rip.deadcode.abukuma3;

@FunctionalInterface
public interface AbuExceptionHandler {
    public AbuResponse handleException( Exception e );
}
