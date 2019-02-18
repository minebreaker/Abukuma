package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.renderer.AbuRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static rip.deadcode.abukuma3.gson.internal.GsonUtils.isAnnotatedBy;


public final class GsonRenderer implements AbuRenderer {

    private final Gson gson;
    private final boolean requireAnnotation;

    public GsonRenderer( Gson gson ) {
        this.gson = gson;
        this.requireAnnotation = true;
    }

    @Override public boolean render( OutputStream os, Object body ) throws IOException {

        if ( requireAnnotation && !isAnnotatedBy( body.getClass(), JsonBody.class ) ) {
            return false;
        }

        try ( OutputStreamWriter osw = new OutputStreamWriter( os ) ) {
            gson.toJson( body, osw );
        }
        return true;
    }
}
