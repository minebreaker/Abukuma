package rip.deadcode.abukuma3.renderer.internal;

import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;


public final class CharSequenceRenderer implements AbuRenderer {

    @Nullable @Override public AbuRenderingResult render( AbuResponse responseCandidate ) {

        if ( !( responseCandidate.body() instanceof CharSequence ) ) {
            return null;
        }

        // TODO charset
        return new AbuRenderingResult(
                os -> os.write( ( (CharSequence) responseCandidate.body() ).toString().getBytes( StandardCharsets.UTF_8 ) ),
                () -> Renderers.ifNotSet( responseCandidate, "text/plain; charset=utf-8" )
        );
    }
}
