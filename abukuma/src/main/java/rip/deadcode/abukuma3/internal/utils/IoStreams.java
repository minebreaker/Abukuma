package rip.deadcode.abukuma3.internal.utils;

import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public final class IoStreams {

    private IoStreams() {
        throw new Error();
    }

    public static String is2str( InputStream inputStream ) {
        return is2str( inputStream, StandardCharsets.UTF_8 );
    }

    public static String is2str( InputStream inputStream, Charset charset ) {
        try ( Reader input = new BufferedReader( new InputStreamReader( inputStream, charset ) ) ) {
            return CharStreams.toString( input );
        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }

    public static InputStream str2is( String value ) {
        return new ByteArrayInputStream( value.getBytes( StandardCharsets.UTF_8 ) );
    }
}
