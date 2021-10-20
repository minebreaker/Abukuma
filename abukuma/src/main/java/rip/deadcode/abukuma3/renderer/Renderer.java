package rip.deadcode.abukuma3.renderer;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;
import java.io.IOException;


/**
 * Renderer is not responsible to close the stream.
 */
@FunctionalInterface
public interface Renderer {

    @Nullable
    public RenderingResult render( ExecutionContext context, Response responseCandidate ) throws IOException;

    public default Renderer ifFailed( Renderer downstream ) {
        return ( context, responseCandidate ) -> {
            RenderingResult result = render( context, responseCandidate );
            return result != null ? result : downstream.render( context, responseCandidate );
        };
    }
}
