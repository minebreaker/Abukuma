package rip.deadcode.abukuma3.parser.internal;

import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.parser.FormUrlEncoded;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.InputStream;


/**
 * {@link AbuParser} for application/x-www-form-urlencoded data.
 *
 * @see <a href="https://url.spec.whatwg.org/#urlencoded-parsing">https://url.spec.whatwg.org/#urlencoded-parsing</a>
 */
public final class FormUrlEncodedParser implements AbuParser<FormUrlEncoded> {

    @Nullable @Override public FormUrlEncoded parse( Class<?> convertTo, InputStream body, AbuRequestHeader header ) {


        return null;
    }
}
