package rip.deadcode.abukuma3.renderer.internal;

import rip.deadcode.abukuma3.internal.utils.IoStreams;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.io.InputStream;

import static rip.deadcode.abukuma3.renderer.internal.CharSequenceRenderer.ifNotSet;


public final class InputStreamRenderer implements AbuRenderer {

    @Nullable @Override public AbuRenderingResult render( AbuResponse responseCandidate ) {

        if ( !( responseCandidate.body() instanceof InputStream ) ) {
            return null;
        }

        return new AbuRenderingResult(
                os -> IoStreams.copy( (InputStream) responseCandidate.body(), os ),
                () -> ifNotSet( responseCandidate, "application/octet-stream" )
        );
    }
}
