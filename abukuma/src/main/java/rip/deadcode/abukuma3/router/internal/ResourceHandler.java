package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.internal.utils.Resources;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;

import static rip.deadcode.abukuma3.router.internal.RoutingUtils.guessMediaType;


public final class ResourceHandler implements AbuHandler {

    private final String path;

    public ResourceHandler( String path ) {
        this.path = path;
    }

    @Override
    public AbuResponse handle( AbuRequest request ) {

        // TODO may add `Content-Disposition: attachment; filename=`?
        // TODO cache

        return AbuResponses.create( Resources.grabResource( path ) )
                           .header( h -> h.contentType( guessMediaType( path ) ) );
    }
}
