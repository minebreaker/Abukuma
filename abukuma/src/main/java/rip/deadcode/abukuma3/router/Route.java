package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.AbuHandler;


public interface Route {

    public RouteMatcher matcher();

    public AbuHandler handler();
}
