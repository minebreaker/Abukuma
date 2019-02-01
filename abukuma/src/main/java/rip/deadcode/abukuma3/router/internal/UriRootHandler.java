package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.net.URI;

public final class UriRootHandler implements AbuHandler {

    private UriRootHandler( URI rootUri ) {

    }

    public static UriRootHandler create( URI rootUri ) {
        return new UriRootHandler( rootUri );
    }

    @Override public AbuResponse handle( AbuRequest request ) {
        return null;  // TODO
    }
}
