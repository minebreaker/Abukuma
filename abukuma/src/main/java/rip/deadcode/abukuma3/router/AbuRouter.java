package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.request.AbuRequestHeader;

@FunctionalInterface
public interface AbuRouter {
    public RoutingContext route( AbuRequestHeader request );
}
