package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;


public final class EmptyRouter implements Router {

    @Nullable @Override public RoutingResult route( RoutingContext context ) {
        return null;
    }

    @Nullable @Override public Handler notFound() {
        return null;
    }
}
