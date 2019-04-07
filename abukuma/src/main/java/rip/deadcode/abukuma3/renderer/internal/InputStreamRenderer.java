package rip.deadcode.abukuma3.renderer.internal;

import com.google.common.io.ByteStreams;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.io.InputStream;

import static rip.deadcode.abukuma3.renderer.internal.Renderers.ifNotSet;


public final class InputStreamRenderer implements AbuRenderer {

    @Nullable @Override public AbuRenderingResult render( AbuExecutionContext context, AbuResponse responseCandidate ) {

        if ( !( responseCandidate.body() instanceof InputStream ) ) {
            return null;
        }

        return new AbuRenderingResult(
                os -> ByteStreams.copy( (InputStream) responseCandidate.body(), os ),
                () -> ifNotSet( responseCandidate, "application/octet-stream" )
        );
    }
}
