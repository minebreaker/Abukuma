package rip.deadcode.abukuma3;


@FunctionalInterface
public interface ServerFactory {
    public AbuServer provide( AbuExecutionContext context );
}
