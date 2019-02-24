package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;


public interface MatchingHandler extends AbuHandler {

    public boolean matches( String uri );
}
