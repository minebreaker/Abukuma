package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.RoutingContext;

import javax.annotation.Nullable;
import java.util.Objects;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;


public final class MethodMatcher implements RouteMatcher {

    private final String method;

    public MethodMatcher( String method ) {
        this.method = method;
    }

    @Nullable @Override public PersistentMap<String, String> matches( RoutingContext context ) {
        return Objects.equals( method, context.header().method() )
               ? createMap()
               : null;
    }
}
