package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.io.InputStream;


public final class InputStreamParser implements Parser {

    @Override
    public InputStream parse( ExecutionContext context, Class<?> cls, InputStream body, RequestHeader header ) {

        if ( cls.isAssignableFrom( InputStream.class ) ) {
            return body;
        }

        return null;
    }
}
