package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static rip.deadcode.abukuma3.gson.internal.GsonUtils.checkHeader;

public final class GsonParser implements AbuParser<Object> {

    private final Gson gson;
    private final boolean requireAnnotation;
    private final boolean requireHeader;

    public GsonParser( Gson gson ) {
        this.gson = gson;
        this.requireAnnotation = true;
        this.requireHeader = true;
    }

    @Nullable @Override public Object parse( Class<?> convertTo, InputStream body, AbuRequestHeader header ) {

        // Annotation check
        if ( requireAnnotation && !GsonUtils.isAnnotatedBy( convertTo, JsonBody.class ) ) {
            return null;
        }

        // Header check
        if ( requireHeader && !checkHeader( header.contentType() ) ) {
            return null;
        }

        Charset charset = header.charset().orElse( StandardCharsets.UTF_8 );
        return gson.fromJson( new InputStreamReader( body, charset ), convertTo );
    }
}
