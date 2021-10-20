package rip.deadcode.abukuma3.internal.utils;


@FunctionalInterface
public interface CheckedUnaryOperator<T, E extends Exception> {
    public T apply( T arg ) throws E;
}
