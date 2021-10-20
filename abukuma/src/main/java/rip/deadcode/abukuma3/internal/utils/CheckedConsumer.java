package rip.deadcode.abukuma3.internal.utils;


@FunctionalInterface
public interface CheckedConsumer<T, E extends Exception> {
    public void accept( T value ) throws Exception;
}
