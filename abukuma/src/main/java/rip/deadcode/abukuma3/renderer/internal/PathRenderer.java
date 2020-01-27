package rip.deadcode.abukuma3.renderer.internal;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.utils.MimeDetector;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import javax.cache.Cache;
import javax.cache.CacheManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;


public class PathRenderer implements AbuRenderer {

    @Nullable @Override public AbuRenderingResult render(
            AbuExecutionContext context, AbuResponse responseCandidate ) throws IOException {

        if ( !( responseCandidate.body() instanceof Path ) ) {
            return null;
        }

        MimeDetector mimeDetector = checkNotNull( context.get( MimeDetector.class ) );
        Path body = (Path) responseCandidate.body();

        String mime = mimeDetector.detectMime( body.toString(), body );

        CacheManager cacheManager = context.get( CacheManager.class, "java.nio.file.Path" );
        if ( cacheManager != null ) {
            Cache<String, byte[]> cache = cacheManager.getCache( "java.nio.file.Path", String.class, byte[].class );

            String key = body.normalize().toAbsolutePath().toString();

            byte[] cached = cache.get( key );
            if ( cached == null ) {
                byte[] file = Files.readAllBytes( body );
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

        return new AbuRenderingResult(
                os -> Files.copy( body, os ),
                () -> Renderers.ifNotSet( responseCandidate, mime )
        );
    }
}
