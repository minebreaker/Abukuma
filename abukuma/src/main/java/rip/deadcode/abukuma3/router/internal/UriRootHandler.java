package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;


public final class UriRootHandler implements MatchingHandler {

    private final String mappingRoot;
    private final String fileSystemRootUri;
    private final Function<String, Optional<URI>> resolver;

    private UriRootHandler( String mappingRoot, String fileSystemRootUri, Function<String, Optional<URI>> resolver ) {
        this.mappingRoot = mappingRoot.endsWith( "/" ) ? mappingRoot : mappingRoot + "/";
        this.fileSystemRootUri = fileSystemRootUri;
        this.resolver = resolver;
    }

    public static UriRootHandler create( String mappingRoot, String fileSystemRootUri, Function<String, Optional<URI>> resolver ) {
        return new UriRootHandler( mappingRoot, fileSystemRootUri, resolver );
    }

    private Optional<URI> resolve( String uri ) {

        // Check mapping
        if ( !uri.startsWith( mappingRoot ) ) {
            return Optional.empty();
        }

        String target = uri.substring( mappingRoot.length() );
        String targetFileStr = Paths.get( fileSystemRootUri ).resolve( target ).normalize().toString();
        String resolvedRoot = Paths.get( fileSystemRootUri ).normalize().toString();

        // Directory traversal check
        if ( !targetFileStr.startsWith( resolvedRoot ) ) {
            return Optional.empty();
        }

        // TODO index.html
        return resolver.apply( targetFileStr );
    }

    @Override public boolean matches( String uri ) {
        return resolve( uri ).isPresent();
    }

    @Override public AbuResponse handle( AbuRequest request ) {

        URI requestedFile = resolve( request.requestUri() ).orElseThrow( RuntimeException::new );
        return UriHandler.handle( request, requestedFile );
    }
}
