package rip.deadcode.abukuma3.renderer.internal;

import com.google.common.io.ByteStreams;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;
import java.io.InputStream;

import static rip.deadcode.abukuma3.renderer.internal.Renderers.ifNotSet;


public final class InputStreamRenderer implements Renderer {

    @Nullable @Override public RenderingResult render( ExecutionContext context, Response responseCandidate ) {

        if ( !( responseCandidate.body() instanceof InputStream ) ) {
            return null;
        }

        return new RenderingResult(
                os -> ByteStreams.copy( (InputStream) responseCandidate.body(), os ),
                () -> ifNotSet( responseCandidate, "application/octet-stream" )
        );
    }
}
