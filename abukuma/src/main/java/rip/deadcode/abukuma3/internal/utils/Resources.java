package rip.deadcode.abukuma3.internal.utils;

import java.io.InputStream;
import java.util.Optional;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


public final class Resources {

    private static Optional<InputStream> getUrl( String path ) {
        return coalesce(
                Resources.class.getClassLoader().getResourceAsStream( path ),
                () -> Thread.currentThread().getContextClassLoader().getResourceAsStream( path )
        );
    }

    public static Optional<InputStream> mayGrabResource( String path ) {
        return getUrl( path );
    }

    public static InputStream grabResource( String path ) {
        return getUrl( path ).orElseThrow( () -> new IllegalArgumentException(
                String.format( "Could not locate the resource of the pass '%s'.", path ) ) );
    }
}
