package rip.deadcode.abukuma3;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class StringRenderer implements ARenderer {

    @Override public boolean render( OutputStream os, Object body ) throws IOException {
        if ( body instanceof String ) {
            os.write( ( (String) body ).getBytes( StandardCharsets.UTF_8 ) );
            return true;
        } else {
            return false;
        }
    }
}
