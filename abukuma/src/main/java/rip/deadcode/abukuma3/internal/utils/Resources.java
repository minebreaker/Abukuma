package rip.deadcode.abukuma3.internal.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


public final class Resources {

    private static Optional<URL> getUrl( String path ) {
        return coalesce(
                Resources.class.getClassLoader().getResource( path ),
                () -> Thread.currentThread().getContextClassLoader().getResource( path )
        );
    }

    public static Optional<URI> mayGrabResource( String path ) {

        return getUrl( path ).map( url -> {
            try {
                return url.toURI();
            } catch ( URISyntaxException e ) {
                return null;
            }
        } );
    }

    public static URI grabResource( String path ) {

        URL location = getUrl( path ).orElseThrow( () -> new IllegalArgumentException(
                String.format( "Could not locate the resource of the pass '%s'.", path ) ) );

        try {
            return location.toURI();
        } catch ( URISyntaxException e ) {
            throw new RuntimeException( e );
        }
    }
}
