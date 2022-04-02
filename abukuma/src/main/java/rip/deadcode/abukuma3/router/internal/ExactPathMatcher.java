package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.RoutingContext;

import javax.annotation.Nullable;
import java.util.Objects;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;


public final class ExactPathMatcher implements RouteMatcher {

    private final String pattern;

    public ExactPathMatcher( String pattern ) {
        this.pattern = pattern;
    }

    @Nullable @Override public PersistentMap<String, String> matches( RoutingContext context ) {
        return Objects.equals( context.header().urlString(), pattern )
               ? createMap()
               : null;
    }
}
