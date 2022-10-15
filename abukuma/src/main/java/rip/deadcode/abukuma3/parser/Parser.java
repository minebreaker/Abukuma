package rip.deadcode.abukuma3.parser;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;


/**
 * Parser implementations may close the {@link InputStream}, but not responsible for doing so.
 */
@FunctionalInterface
public interface Parser {

    @Nullable
    public Object parse( ExecutionContext context, Class<?> convertTo, InputStream body, RequestHeader header )
            throws IOException;

    public default Parser ifFailed( Parser downstream ) {
        return ( ec, c, is, header ) -> {
            Object result = parse( ec, c, is, header );
            return result != null ? result : downstream.parse( ec, c, is, header );
        };
    }
}
