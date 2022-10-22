package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static rip.deadcode.abukuma3.gson.internal.GsonUtils.checkHeader;


public final class GsonParser implements Parser {

    private final boolean requireAnnotation;
    private final boolean requireHeader;

    public GsonParser(boolean requireJsonMimeType, boolean requireAnnotation) {
        this.requireHeader = requireJsonMimeType;
        this.requireAnnotation = requireAnnotation;
    }

    @Nullable @Override
    public Object parse( ExecutionContext context, Class<?> convertTo, InputStream body, RequestHeader header ) {

        // Annotation check
        if ( requireAnnotation && !GsonUtils.isAnnotatedBy( convertTo, JsonBody.class ) ) {
            return null;
        }

        // Header check
        if ( requireHeader && !checkHeader( header.contentType() ) ) {
            return null;
        }

        Gson gson = context.get( Gson.class );

        Charset charset = header.charset().orElse( StandardCharsets.UTF_8 );
        return gson.fromJson( new InputStreamReader( body, charset ), convertTo );
    }
}
