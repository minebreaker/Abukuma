package rip.deadcode.abukuma3.renderer.internal;

import com.google.common.base.Strings;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;


public final class CharSequenceRenderer implements AbuRenderer {

//    @Override public boolean render( OutputStream os, Object body ) throws IOException {
//        if ( body instanceof CharSequence ) {
//            os.write( ( (CharSequence) body ).toString().getBytes( StandardCharsets.UTF_8 ) );
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Nullable @Override public AbuRenderingResult render( AbuResponse responseCandidate ) {

        if ( !( responseCandidate.body() instanceof CharSequence ) ) {
            return null;
        }

        return new AbuRenderingResult(
                os -> os.write( ( (CharSequence) responseCandidate.body() ).toString().getBytes( StandardCharsets.UTF_8 ) ),
                () -> ifNotSet( responseCandidate, "text/plain; charset=utf-8" )
        );
    }

    static AbuResponse ifNotSet( AbuResponse base, String mime ) {

        if ( !Strings.isNullOrEmpty( base.header().contentType() ) ) {
            return base;
        }

        return base.header( h -> h.contentType( mime ) );
    }
}
