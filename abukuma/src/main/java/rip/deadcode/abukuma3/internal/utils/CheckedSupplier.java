package rip.deadcode.abukuma3.internal.utils;

@FunctionalInterface
public interface CheckedSupplier<T, E extends Exception> {
    public T get() throws E;
}
