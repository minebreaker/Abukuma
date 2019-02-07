package rip.deadcode.abukuma3.internal.utils;

@FunctionalInterface
public interface CheckedRunnable<E extends Exception> {
    public void run() throws E;
}
