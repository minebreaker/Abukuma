package rip.deadcode.abukuma3.renderer.internal;

import com.google.common.io.ByteStreams;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.utils.MimeDetector;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import javax.cache.Cache;
import javax.cache.CacheManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;


public final class FileRenderer implements AbuRenderer {

    @Nullable @Override public AbuRenderingResult render(
            AbuExecutionContext context, AbuResponse responseCandidate ) throws IOException {

        Object bodyObj = responseCandidate.body();

        if ( !( bodyObj instanceof File ) ) {
            return null;
        }

        MimeDetector mimeDetector = checkNotNull( context.get( MimeDetector.class ) );
        File body = (File) bodyObj;

        String mime = mimeDetector.detectMime( body.toString(), null );

        CacheManager cacheManager = context.get( CacheManager.class, "java.io.File" );
        if ( cacheManager != null ) {
            Cache<String, byte[]> cache = cacheManager.getCache( "java.io.File", String.class, byte[].class );

            String key = body.getCanonicalPath();
            byte[] cached = cache.get( key );
            if ( cache == null ) {
                byte[] file = ByteStreams.toByteArray( new FileInputStream( body ) );
                cache.put( key, file );
                return new AbuRenderingResult(
                        os -> os.write( file ),
                        () -> Renderers.ifNotSet( responseCandidate, mime )
                );

            } else {
                return new AbuRenderingResult(
                        os -> os.write( cached ),
                        () -> Renderers.ifNotSet( responseCandidate, mime )
                );
            }
        }

        InputStream is = new FileInputStream( body );
        return new AbuRenderingResult(
                os -> ByteStreams.copy( is, os ),
                () -> Renderers.ifNotSet( responseCandidate, mime )
        );
    }
}
