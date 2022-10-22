package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static rip.deadcode.abukuma3.gson.internal.GsonUtils.isAnnotatedBy;


public final class GsonRenderer implements Renderer {

    private final boolean requireAnnotation;

    public GsonRenderer( boolean requireAnnotation ) {
        this.requireAnnotation = requireAnnotation;
    }

    @Nullable @Override public RenderingResult render( ExecutionContext context, Response responseCandidate )
            throws IOException {

        Object body = responseCandidate.body();

        if ( requireAnnotation && !isAnnotatedBy( body.getClass(), JsonBody.class ) ) {
            return null;
        }

        Gson gson = context.get( Gson.class );

        return new RenderingResult(
                os -> {
                    try ( OutputStreamWriter osw = new OutputStreamWriter( os ) ) {
                        gson.toJson( body, osw );
                    }
                },
                () -> responseCandidate.header( h -> h.contentType( "application/json" ) )
        );
    }
}
