package rip.deadcode.abukuma3.router.internal;

import org.jetbrains.annotations.Nullable;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.RoutingContext;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;


public final class PathVariableMatcher implements RouteMatcher {

    private final String variableName;
    private final int index;

    public PathVariableMatcher( String variableName, int index ) {
        this.variableName = variableName;
        this.index = index;
    }

    @Nullable @Override public PersistentMap<String, String> matches( RoutingContext context ) {
        var path = context.header().urlPaths();
        if ( path.size() <= index ) {
            return null;
        }

        return createMap( variableName, path.get( index ) );
    }
}
