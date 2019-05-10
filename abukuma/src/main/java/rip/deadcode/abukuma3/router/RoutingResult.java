package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.AbuHandler;

import java.util.Map;


public interface RoutingResult {

    public AbuHandler handler();

    public Map<String, String> parameters();
}
