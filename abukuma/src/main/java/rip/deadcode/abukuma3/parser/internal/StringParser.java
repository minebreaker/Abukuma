package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.io.InputStream;

import static rip.deadcode.abukuma3.internal.utils.IoStreams.is2str;

public final class StringParser implements Parser<String> {

    @Override
    public String parse( Class<?> cls, InputStream body, RequestHeader header ) {

        if ( cls.isAssignableFrom( String.class ) ) {
            return is2str( body );  // TODO charset
        }

        return null;
    }
}
