package rip.deadcode.abukuma3.parser.internal;


import org.jetbrains.annotations.Nullable;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.io.IOException;
import java.io.InputStream;


/**
 * The parser implementation for the empty body
 * which is typical for GET requests.
 */
public final class EmptyParser implements Parser<Request.Empty> {

    @Nullable @Override
    public Request.Empty parse( ExecutionContext context, Class<?> convertTo, InputStream body, RequestHeader header )
            throws IOException {

        if ( convertTo.isAssignableFrom( Request.Empty.class ) ) {
            return Request.Empty.singleton;
        }

        return null;
    }
}
