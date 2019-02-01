package rip.deadcode.abukuma3.parser.internal;

import com.google.common.io.CharStreams;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public final class StringParser implements AbuParser<String> {

    @Override
    public String parse( Class<?> cls, InputStream body, AbuRequestHeader header ) {

        if ( cls.isAssignableFrom( String.class ) ) {

            try ( InputStreamReader is = new InputStreamReader( body, StandardCharsets.UTF_8 ) ) {
                return CharStreams.toString( is );

            } catch ( IOException e ) {
                throw new UncheckedIOException( e );
            }
        }

        return null;
    }
}
