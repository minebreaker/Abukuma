package rip.deadcode.abukuma3.internal.utils;


@FunctionalInterface
public interface Function3<T1, T2, T3, R, E extends Exception> {

    public R apply( T1 arg1, T2 arg2, T3 arg3 ) throws E;
}
