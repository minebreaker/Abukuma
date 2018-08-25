package rip.deadcode.abukuma3.parser;

import com.google.common.io.CharStreams;
import rip.deadcode.abukuma3.request.AbuRequestHeader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static rip.deadcode.akashi.util.Uncheck.tryUncheck;

public final class StringParser implements AbuParser<String> {

    @Override public String parse( InputStream body, AbuRequestHeader header ) {
        return tryUncheck( () -> CharStreams.toString( new InputStreamReader( body, StandardCharsets.UTF_8 ) ) );
    }
}
