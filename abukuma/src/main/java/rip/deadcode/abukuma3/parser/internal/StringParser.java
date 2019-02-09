package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.InputStream;

import static rip.deadcode.abukuma3.internal.utils.IoStreams.is2str;

public final class StringParser implements AbuParser<String> {

    @Override
    public String parse( Class<?> cls, InputStream body, AbuRequestHeader header ) {

        if ( cls.isAssignableFrom( String.class ) ) {
            return is2str( body );  // TODO charset
        }

        return null;
    }
}
