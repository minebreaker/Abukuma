package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.request.AbuRequest;
import rip.deadcode.abukuma3.response.AbuResponse;

@FunctionalInterface
public interface AbuHandler {
    public AbuResponse handle( AbuRequest request );
}
