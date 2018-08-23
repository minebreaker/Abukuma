package rip.deadcode.abukuma3;

@FunctionalInterface
public interface AbuHandler {
    public AbuResponse handle( AbuRequest request );
}
