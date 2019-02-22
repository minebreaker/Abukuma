package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static rip.deadcode.abukuma3.gson.internal.GsonUtils.isAnnotatedBy;


public final class GsonRenderer implements AbuRenderer {

    private final Gson gson;
    private final boolean requireAnnotation;

    public GsonRenderer( Gson gson ) {
        this.gson = gson;
        this.requireAnnotation = true;
    }

    @Nullable @Override public AbuRenderingResult render( AbuResponse responseCandidate ) throws IOException {

        Object body = responseCandidate.body();

        if ( requireAnnotation && !isAnnotatedBy( body.getClass(), JsonBody.class ) ) {
            return null;
        }

        return new AbuRenderingResult(
                os -> {
                    try ( OutputStreamWriter osw = new OutputStreamWriter( os ) ) {
                        gson.toJson( body, osw );
                    }
                },
                () -> responseCandidate.header( h -> h.contentType( "application/json" ) )
        );
    }
}
