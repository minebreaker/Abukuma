package rip.deadcode.abukuma3.parser.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.parser.UrlEncoded;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


/**
 * {@link Parser} for application/x-www-form-urlencoded data.
 *
 * @see <a href="https://url.spec.whatwg.org/#urlencoded-parsing">https://url.spec.whatwg.org/#urlencoded-parsing</a>
 */
public final class UrlEncodedParser implements Parser<UrlEncoded> {

    @Nullable @Override public UrlEncoded parse(
            Class<?> convertTo, InputStream body, RequestHeader header ) throws IOException {

        if ( !convertTo.equals( UrlEncoded.class ) ) {
            return null;
        }

        ListMultimap<String, String> result = parse( new BufferedInputStream( body ) );
        return UrlEncoded.create( result );
    }

    @VisibleForTesting
    static ListMultimap<String, String> parse( InputStream is ) throws IOException {

        ListMultimap<String, String> out = ArrayListMultimap.create();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ( true ) {
            ListMultimap<String, String> ret = parsePair( is, out, baos );
            if ( ret == null ) {
                return out;
            } else {
                out = ret;
            }
        }
    }

    private static ListMultimap<String, String> parsePair(
            InputStream is, ListMultimap<String, String> parsed, ByteArrayOutputStream baos ) throws IOException {

        is.mark( 1 );
        int i = is.read();

        if ( i == -1 ) {
            return null;

        } else if ( i == and ) {
            return parsed;

        } else if ( i == equal ) {
            // Empty key
            String v = parseKeyOrValue( is, baos, true );
            parsed.put( "", v );  // The implementation should allow duplicate key
            return parsed;

        } else {
            is.reset();
            String k = parseKeyOrValue( is, baos, false );

            is.mark( 1 );
            i = is.read();

            if ( i == and ) {  // Does not have a value
                parsed.put( k, "" );
                return parsed;
            }

            String v = parseKeyOrValue( is, baos, true );
            parsed.put( k, v );  // The implementation should allow duplicate key
            return parsed;
        }
    }

    private static final int equal = '=';
    private static final int and = '&';
    private static final int plus = '+';

    private static String parseKeyOrValue( InputStream is, ByteArrayOutputStream baos, boolean allowEqual ) throws IOException {

        baos.reset();

        while ( true ) {
            is.mark( 1 );
            int i = is.read();

            if ( i == -1 || !allowEqual && i == equal || i == and ) {
                is.reset();
                return URLDecoder.decode( new String( baos.toByteArray(), StandardCharsets.UTF_8 ), "UTF-8" );
            }

            if ( i == plus ) {
                baos.write( ' ' );
            } else {
                baos.write( i );
            }
        }
    }
}
