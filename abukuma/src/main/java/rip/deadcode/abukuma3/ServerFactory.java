package rip.deadcode.abukuma3;


@FunctionalInterface
public interface ServerFactory {
    public Server provide( ExecutionContext context );
}
