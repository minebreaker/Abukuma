package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.RoutingContext;

import javax.annotation.Nullable;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;


public final class PathSegmentSizeMatcher implements RouteMatcher {

    private final int size;

    public PathSegmentSizeMatcher( int size ) {
        this.size = size;
    }

    @Nullable @Override public PersistentMap<String, String> matches( RoutingContext context ) {
        return context.header().urlPaths().size() == size
               ? createMap()
               : null;
    }
}
