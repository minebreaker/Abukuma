package rip.deadcode.abukuma3.renderer.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.utils.MimeDetector;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;
import javax.cache.Cache;
import javax.cache.CacheManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


public class PathRenderer implements Renderer {

    @Nullable @Override public RenderingResult render(
            ExecutionContext context, Response responseCandidate ) throws IOException {

        Object bodyObj = responseCandidate.body();
        if ( !( bodyObj instanceof Path ) ) {
            return null;
        }

        MimeDetector mimeDetector = context.get( MimeDetector.class );
        Path body = (Path) bodyObj;

        String mime = mimeDetector.detect( body.toString(), body );

        String cacheKey = "java.nio.file.Path";
        Optional<CacheManager> possibleCacheManager = context.mayGet( CacheManager.class, cacheKey );
        if ( possibleCacheManager.isPresent() ) {
            CacheManager cacheManager = possibleCacheManager.get();
            Cache<String, byte[]> cache = cacheManager.getCache( "java.nio.file.Path", String.class, byte[].class );

            String key = body.normalize().toAbsolutePath().toString();
            byte[] cached = cache.get( key );
            if ( cached == null ) {
                byte[] file = Files.readAllBytes( body );
                cache.put( key, file );
                return new RenderingResult(
                        os -> os.write( file ),
                        () -> Renderers.ifNotSet( responseCandidate, mime )
                );

            } else {
                return new RenderingResult(
                        os -> os.write( cached ),
                        () -> Renderers.ifNotSet( responseCandidate, mime )
                );
            }
        }

        return new RenderingResult(
                os -> Files.copy( body, os ),
                () -> Renderers.ifNotSet( responseCandidate, mime )
        );
    }
}
