package rip.deadcode.abukuma3.internal.utils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;


public final class MoreMoreObjects {

    @SafeVarargs
    public static <T> Optional<T> coalesce( @Nullable T first, Supplier<? extends T> second, Supplier<? extends T>... rest ) {
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
}
