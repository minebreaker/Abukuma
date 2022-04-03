package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.StandardRouter;

import javax.annotation.Nullable;


public final class StandardRouterImpl implements StandardRouter {

    private final Router delegate;

    public StandardRouterImpl( Router delegate ) {
        this.delegate = delegate;
    }

    @Nullable @Override public RoutingResult route( RoutingContext context ) {
        return delegate.route( context );
    }

    @Override public Handler notFound() {
        return delegate.notFound();
    }

    @Override public StandardRouter notFound( Handler handler ) {
        return new StandardRouterImpl( delegate.notFound( handler ) );
    }

    @Override public StandardRouter ifNotMatch( Router downstream ) {
        return new StandardRouterImpl( delegate.ifNotMatch( downstream ) );
    }

    @Override public StandardRouter path( String method, String pattern, Handler handler ) {
        return this.ifNotMatch( new PathMatchingRouterImpl( method, pattern, handler, null ) );
    }

    @Override public StandardRouter ofMatcher( RouteMatcher matcher, Handler handler ) {
        return this.ifNotMatch( new MatcherRouter( matcher, handler, null ) );
    }
}
