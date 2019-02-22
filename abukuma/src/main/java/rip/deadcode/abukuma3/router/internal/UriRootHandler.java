package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.net.URI;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;


public final class UriRootHandler implements AbuHandler {

    // URL           /context/router-root/dir/file.txt
    // Mapping Root  /context/router-root
    // File Root     /etc/http/
    // File          /etc/http/dir/file.txt
    // Request       /context/router-root/dir/file.txt

    private final String mappingRoot;
    private final Supplier<String> fileSystemRootUri;
    private final Function<String, URI> resolver;

    private UriRootHandler( String mappingRoot, Supplier<String> fileSystemRootUri, Function<String, URI> resolver ) {
        this.mappingRoot = mappingRoot;
        this.fileSystemRootUri = fileSystemRootUri;
        this.resolver = resolver;
    }

    public static UriRootHandler create( String mappingRoot, Supplier<String> fileSystemRootUri, Function<String, URI> resolver ) {
        return new UriRootHandler( mappingRoot, fileSystemRootUri, resolver );
    }

    @Override public AbuResponse handle( AbuRequest request ) {

        assert mappingRoot.startsWith( request.toString() );

        // TODO index.html
        String target = request.requestUri().substring( mappingRoot.length() + 1 );
        URI requestedFile = resolver.apply( Paths.get( fileSystemRootUri.get() ).resolve( target ).toString() );

        return UriHandler.handle( request, requestedFile );
    }
}
