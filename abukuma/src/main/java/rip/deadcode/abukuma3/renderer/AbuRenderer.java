package rip.deadcode.abukuma3.renderer;

import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.io.IOException;


/**
 * Renderer is not responsible to close the stream.
 */
@FunctionalInterface
public interface AbuRenderer {

    @Nullable
    public AbuRenderingResult render( AbuResponse responseCandidate ) throws IOException;

    public default AbuRenderer ifFailed( AbuRenderer downstream ) {
        return responseCandidate -> {
            AbuRenderingResult result = render( responseCandidate );
            return result != null ? result : downstream.render( responseCandidate );
        };
    }
}
