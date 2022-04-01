package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;
import rip.deadcode.abukuma3.router.StandardRouter;

import javax.annotation.Nullable;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


public final class StandardRouterImpl implements StandardRouter {

    @Nullable private final Router upstream;
    private final Router downstream;

    // TODO 合成用のルーターを別に作る?
    public StandardRouterImpl( Router router ) {
        this.upstream = null;
        this.downstream = router;
    }

    public StandardRouterImpl( @Nullable Router upstream, Router downstream ) {
        this.upstream = upstream;
        this.downstream = downstream;
    }

    @Nullable @Override public RoutingResult route( RoutingContext context ) {
        if ( upstream == null ) {
            return downstream.route( context );
        } else {
            RoutingResult result = upstream.route( context );
            return result != null ? result : downstream.route( context );
        }
    }

    @Override public Handler notFound() {
        if ( upstream == null ) {
            return downstream.notFound();
        } else {
            return coalesce( upstream.notFound(), () -> downstream.notFound() ).orElse( null );
        }
    }

    @Override public StandardRouter notFound( Handler handler ) {
        return this.ifNotMatch( new NotFoundRouter( handler ) );
    }

    @Override public StandardRouter ifNotMatch( Router downstream ) {
        return new StandardRouterImpl( upstream, downstream );
    }

    @Override public StandardRouter path( String method, String pattern, Handler handler ) {
        return this.ifNotMatch( new PathMatchingRouterImpl( method, pattern, handler, null ) );
    }

    @Override public StandardRouter ofMatcher( RouteMatcher matcher, Handler handler ) {
        return this.ifNotMatch( new MatcherRouter( matcher, handler, null ) );
    }
}
