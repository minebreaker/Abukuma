package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.AbuHandler;

import java.util.Map;

public interface AbuRoutingContext {

    public Map<String, String> getPathParams();

    public AbuHandler getHandler();
}
