package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.Route;
import rip.deadcode.abukuma3.router.RouteMatcher;


public class RouteImpl implements Route {

    private final RouteMatcher matcher;
    private final Handler handler;

    public RouteImpl( RouteMatcher matcher, Handler handler ) {
        this.matcher = matcher;
        this.handler = handler;
    }

    @Override
    public RouteMatcher matcher() {
        return matcher;
    }

    @Override
    public Handler handler() {
        return handler;
    }
}
