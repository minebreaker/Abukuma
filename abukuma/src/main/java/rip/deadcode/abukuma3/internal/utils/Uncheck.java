package rip.deadcode.abukuma3.internal.utils;

import java.util.function.Function;


public final class Uncheck {

    private Uncheck() {
        throw new Error();
    }

    @SuppressWarnings( "unchecked" )
    public static <T, E extends Exception> T uncheck(
            CheckedSupplier<T, E> supplier, Function<E, ? extends RuntimeException> wrapper ) {
        try {
            return supplier.get();
        } catch ( Exception t ) {
            throw wrapper.apply( (E) t );
        }
    }

    public static <T, E extends Exception> T uncheck( CheckedSupplier<T, E> supplier ) {
        return uncheck( supplier, RuntimeException::new );
    }

    @SuppressWarnings( "unchecked" )
    public static <E extends Exception> void uncheck(
            CheckedRunnable<E> runner, Function<E, ? extends RuntimeException> wrapper ) {
        try {
            runner.run();
        } catch ( Exception t ) {
            throw wrapper.apply( (E) t );
        }
    }

    public static <E extends Exception> void uncheck( CheckedRunnable<E> runner ) {
        uncheck( runner, RuntimeException::new );
    }
}
