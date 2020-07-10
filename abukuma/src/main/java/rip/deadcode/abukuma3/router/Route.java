package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.Handler;


public interface Route {

    public RouteMatcher matcher();

    public Handler handler();
}
