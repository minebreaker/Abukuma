package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.annotation.Nullable;
import java.io.InputStream;

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



        return null;
    }
}
