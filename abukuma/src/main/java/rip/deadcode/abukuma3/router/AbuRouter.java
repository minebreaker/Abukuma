package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.value.AbuRequestHeader;

@FunctionalInterface
public interface AbuRouter {
    public RoutingContext route( AbuRequestHeader request );
}