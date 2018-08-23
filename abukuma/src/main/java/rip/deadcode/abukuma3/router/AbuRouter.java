package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.request.AbuRequestHeader;
import rip.deadcode.abukuma3.handler.AbuHandler;

@FunctionalInterface
public interface AbuRouter {
    public AbuHandler route( AbuRequestHeader request);
}
