package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.InputStream;

public final class InputStreamParser implements AbuParser<InputStream> {

    @Override
    public InputStream parse( Class<?> cls, InputStream body, AbuRequestHeader header ) {

        if ( cls.isAssignableFrom( InputStream.class ) ) {
            return body;
        }

        return null;
    }
}
