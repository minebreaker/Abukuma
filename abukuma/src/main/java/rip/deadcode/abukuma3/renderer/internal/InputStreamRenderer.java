package rip.deadcode.abukuma3.renderer.internal;

import rip.deadcode.abukuma3.internal.utils.IoStreams;
import rip.deadcode.abukuma3.renderer.AbuRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public final class InputStreamRenderer implements AbuRenderer {

    @Override public boolean render( OutputStream os, Object body ) throws IOException {
        if ( body instanceof InputStream ) {
            IoStreams.copy( (InputStream) body, os );
            return true;
        } else {
            return false;
        }
    }
}
