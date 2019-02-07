package rip.deadcode.abukuma3.renderer;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface AbuRenderer {

    public boolean render( OutputStream os, Object body ) throws IOException;
    // @Nullable public UnaryOperator<AbuHeader> render( OutputStream os, Object body );

    public default AbuRenderer ifFailed( AbuRenderer downstream ) {
        return ( os, body ) ->
                render( os, body ) || downstream.render( os, body );
    }
}
