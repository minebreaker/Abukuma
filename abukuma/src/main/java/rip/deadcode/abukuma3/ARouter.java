package rip.deadcode.abukuma3;

@FunctionalInterface
public interface ARouter {
    public AHandler route(ARequestHeader request);
}
