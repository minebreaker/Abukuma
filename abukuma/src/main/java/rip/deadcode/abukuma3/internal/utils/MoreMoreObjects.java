package rip.deadcode.abukuma3.internal.utils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class MoreMoreObjects {

    @SafeVarargs
    public static <T> Optional<T> coalesce(
            @Nullable T first,
            Supplier<? extends T> second,
            Supplier<? extends T>... rest ) {
        if ( first != null ) {
            return Optional.of( first );
        }
        T secondVal = second.get();
        if ( secondVal != null ) {
            return Optional.of( secondVal );
        }

        for ( Supplier<? extends T> s : rest ) {
            T val = s.get();
            if ( val != null ) {
                return Optional.of( val );
            }
        }
        return Optional.empty();
    }

    public static <T> T also( T value, CheckedConsumer<T, ?> f ) {
        uncheck( () -> f.accept( value ) );
        return value;
    }
}
