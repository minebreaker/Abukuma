package rip.deadcode.abukuma3.renderer;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.io.IOException;


/**
 * Renderer is not responsible to close the stream.
 */
@FunctionalInterface
public interface AbuRenderer {

    @Nullable
    public AbuRenderingResult render( AbuExecutionContext context, AbuResponse responseCandidate ) throws IOException;

    public default AbuRenderer ifFailed( AbuRenderer downstream ) {
        return ( context, responseCandidate ) -> {
            AbuRenderingResult result = render( context, responseCandidate );
            return result != null ? result : downstream.render( context, responseCandidate );
        };
    }
}
