package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.util.List;


public interface RoutingContext {

    public AbuRequestHeader header();

    public List<String> path();

    public List<String> contextPath();

    public RoutingContext contextPath( List<String> contextPath );
}
