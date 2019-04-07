package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static rip.deadcode.abukuma3.gson.internal.GsonUtils.isAnnotatedBy;


public final class GsonRenderer implements AbuRenderer {

    private final boolean requireAnnotation;

    public GsonRenderer(  ) {
        this.requireAnnotation = true;
    }

    @Nullable @Override public AbuRenderingResult render( AbuExecutionContext context, AbuResponse responseCandidate ) throws IOException {

        Object body = responseCandidate.body();

        if ( requireAnnotation && !isAnnotatedBy( body.getClass(), JsonBody.class ) ) {
            return null;
        }

        Gson gson = context.get( Gson.class );

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
