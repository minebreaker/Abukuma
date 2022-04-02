package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.RoutingContext;

import javax.annotation.Nullable;
import java.util.Objects;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;


public final class PathMatcher implements RouteMatcher {

    private final String pathSegmentPattern;
    private final int index;

    public PathMatcher( String pathSegmentPattern, int index ) {
        this.pathSegmentPattern = pathSegmentPattern;
        this.index = index;
    }

    @Nullable @Override public PersistentMap<String, String> matches( RoutingContext context ) {
        PersistentList<String> path = context.header().urlPaths();

        if ( path.size() <= index ) {
            return null;
        }

        return Objects.equals( path.get( index ), pathSegmentPattern )
               ? createMap()
               : null;
    }
}
