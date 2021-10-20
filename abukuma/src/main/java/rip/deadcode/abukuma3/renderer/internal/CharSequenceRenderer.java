package rip.deadcode.abukuma3.renderer.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;


public final class CharSequenceRenderer implements Renderer {

    @Nullable @Override public RenderingResult render( ExecutionContext context, Response responseCandidate ) {

        if ( !( responseCandidate.body() instanceof CharSequence ) ) {
            return null;
        }

        // TODO charset
        return new RenderingResult(
                os -> os.write( ( (CharSequence) responseCandidate.body() ).toString().getBytes( StandardCharsets.UTF_8 ) ),
                () -> Renderers.ifNotSet( responseCandidate, "text/plain; charset=utf-8" )
        );
    }
}
