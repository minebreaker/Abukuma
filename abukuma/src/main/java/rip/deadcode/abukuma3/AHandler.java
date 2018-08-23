package rip.deadcode.abukuma3;

@FunctionalInterface
public interface AHandler {
    public AResponse handle( ARequest request );
}
