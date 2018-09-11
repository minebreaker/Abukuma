package rip.deadcode.abukuma3.parser;

import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.InputStream;

public final class InputStreamParser implements AbuParser<InputStream> {

    @Override public InputStream parse( InputStream body, AbuRequestHeader header ) {
        return body;
    }
}
