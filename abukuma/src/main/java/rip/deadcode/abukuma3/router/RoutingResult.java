package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.Handler;

import java.util.Map;


public interface RoutingResult {

    public Handler handler();

    public Map<String, String> pathParameters();
}
