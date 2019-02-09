package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.parser.UrlEncoded;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

import static rip.deadcode.abukuma3.internal.utils.IoStreams.is2str;


/**
 * {@link AbuParser} for application/x-www-form-urlencoded data.
 *
 * @see <a href="https://url.spec.whatwg.org/#urlencoded-parsing">https://url.spec.whatwg.org/#urlencoded-parsing</a>
 */
public final class UrlEncodedParser implements AbuParser<UrlEncoded> {

    @Nullable @Override public UrlEncoded parse(
            Class<?> convertTo, InputStream body, AbuRequestHeader header ) throws IOException {

        if ( !convertTo.equals( UrlEncoded.class ) ) {
            return null;
        }

        String s = is2str( body );  // TODO charset





        return null;
    }
}
