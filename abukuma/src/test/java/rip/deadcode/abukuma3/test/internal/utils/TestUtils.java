package rip.deadcode.abukuma3.test.internal.utils;

import com.google.common.io.CharStreams;
import rip.deadcode.abukuma3.internal.utils.Uncheck;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class TestUtils {

    public static InputStream str2is( String value ) {
        return new ByteArrayInputStream( value.getBytes( StandardCharsets.UTF_8 ) );
    }

    public static String is2str( InputStream is ) {
        return Uncheck.uncheck( () -> CharStreams.toString( new InputStreamReader( is ) ) );
    }
}
