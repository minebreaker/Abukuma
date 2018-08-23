package rip.deadcode.abukuma3;

@FunctionalInterface
public interface AbuRouter {
    public AbuHandler route( AbuRequestHeader request);
}
