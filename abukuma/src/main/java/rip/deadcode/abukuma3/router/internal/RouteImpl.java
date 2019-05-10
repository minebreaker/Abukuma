package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.router.Route;
import rip.deadcode.abukuma3.router.RouteMatcher;


public class RouteImpl implements Route {

    private final RouteMatcher matcher;
    private final AbuHandler handler;

    public RouteImpl( RouteMatcher matcher, AbuHandler handler ) {
        this.matcher = matcher;
        this.handler = handler;
    }

    @Override
    public RouteMatcher matcher() {
        return matcher;
    }

    @Override
    public AbuHandler handler() {
        return handler;
    }
}
