package rip.deadcode.abukuma3.parser;

import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Parser implementations may close the {@link InputStream}, but not responsible for doing so.
 * @param <T> Class to be parsed.
 */
@FunctionalInterface
public interface AbuParser<T> {

    @Nullable
    public T parse( Class<?> convertTo, InputStream body, AbuRequestHeader header ) throws IOException;

    public default AbuParser<?> ifFailed( AbuParser<?> downstream ) {
        return ( c, is, header ) -> {
            Object result = parse( c, is, header );
            return result != null ? result : downstream.parse( c, is, header );
        };
    }
}
