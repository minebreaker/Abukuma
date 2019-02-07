package rip.deadcode.abukuma3.internal.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;

public final class Resources {

    public static URI grabResource( String path ) {

        URL location = coalesce(
                Resources.class.getClassLoader().getResource( path ),
                () -> Thread.currentThread().getContextClassLoader().getResource( path )
        ).orElseThrow( () -> new IllegalArgumentException( String.format( "Could not locate the resource of the pass '%s'.", path ) ) );

        try {
            return location.toURI();
        } catch ( URISyntaxException e ) {
            throw new RuntimeException( e );
        }
    }
}
