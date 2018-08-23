package rip.deadcode.abukuma3;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class InputStreamRenderer implements ARenderer {
    @Override public boolean render( OutputStream os, Object body ) throws IOException {
        if ( body instanceof InputStream ) {
            ByteStreams.copy( (InputStream) body, os );
            return true;
        } else {
            return false;
        }
    }
}
