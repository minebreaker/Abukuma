package rip.deadcode.abukuma3;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface ARenderer {

    public boolean render( OutputStream os, Object body ) throws IOException;

    public default ARenderer ifFailed( ARenderer downstream ) {
        return ( os, body ) ->
                render( os, body ) || downstream.render( os, body );
    }
}
