package rip.deadcode.abukuma3.parser;

import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Parser implementations may close the {@link InputStream}, but not responsible for doing so.
 * @param <T> Class to be parsed.
 */
@FunctionalInterface
public interface Parser<T> {

    @Nullable
    public T parse( Class<?> convertTo, InputStream body, RequestHeader header ) throws IOException;

    public default Parser<?> ifFailed( Parser<?> downstream ) {
        return ( c, is, header ) -> {
            Object result = parse( c, is, header );
            return result != null ? result : downstream.parse( c, is, header );
        };
    }
}
