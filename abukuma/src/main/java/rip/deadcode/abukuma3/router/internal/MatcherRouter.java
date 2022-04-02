package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;


public final class MatcherRouter implements Router {

    private final RouteMatcher matcher;
    private final Handler handler;
    @Nullable private final Handler notFound;

    public MatcherRouter( RouteMatcher matcher, Handler handler, @Nullable Handler notFound ) {
        this.matcher = matcher;
        this.handler = handler;
        this.notFound = notFound;
    }

    @Nullable @Override public RoutingResult route( RoutingContext context ) {
        PersistentMap<String, String> result = matcher.matches( context );
        return result != null
               ? new RoutingResultImpl( handler, result )
               : null;
    }

    @Nullable @Override public Handler notFound() {
        return notFound;
    }
}
