package rip.deadcode.abukuma3.renderer;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Renderer is not responsible to close the stream.
 */
@FunctionalInterface
public interface AbuRenderer {

    public boolean render( OutputStream os, Object body ) throws IOException;

    public default AbuRenderer ifFailed( AbuRenderer downstream ) {
        return ( os, body ) ->
                render( os, body ) || downstream.render( os, body );
    }
}
