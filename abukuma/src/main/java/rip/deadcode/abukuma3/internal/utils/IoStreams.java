package rip.deadcode.abukuma3.internal.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class IoStreams {

    private IoStreams() {
        throw new Error();
    }

    public static String is2str( InputStream inputStream ) {
        return is2str( inputStream, StandardCharsets.UTF_8 );
    }

    public static String is2str( InputStream inputStream, Charset charset ) {
        try ( Stream<String> input = new BufferedReader( new InputStreamReader( inputStream, charset ) ).lines() ) {
            return input.collect( joining() );
        }
    }

    public static InputStream str2is( String value ) {
        return new ByteArrayInputStream( value.getBytes( StandardCharsets.UTF_8 ) );
    }
}
