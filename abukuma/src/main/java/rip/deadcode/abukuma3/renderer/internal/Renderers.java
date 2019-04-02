package rip.deadcode.abukuma3.renderer.internal;

import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.util.Optional;


public final class Renderers {

    private Renderers() {}

    static AbuResponse ifNotSet( AbuResponse base, String mime ) {

        Optional<String> contentTypeSet = base.header().mayGet( HttpHeaders.CONTENT_TYPE );

        if ( contentTypeSet.isPresent() ) {
            return base;
        }

        return base.header( h -> h.contentType( mime ) );
    }
}
