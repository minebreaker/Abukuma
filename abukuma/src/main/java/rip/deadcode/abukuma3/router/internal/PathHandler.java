package rip.deadcode.abukuma3.router.internal;


import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;

import java.nio.file.Files;
import java.nio.file.Path;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;
import static rip.deadcode.abukuma3.router.internal.RoutingUtils.guessMediaType;


// TODO remove this and create PathRenderer
public final class PathHandler implements AbuHandler {

    private final Path path;

    public PathHandler( Path path ) {
        this.path = path;
    }

    @Override public AbuResponse handle( AbuExecutionContext context, AbuRequest request ) {
        // TODO file existence check
        // TODO may add `Content-Disposition: attachment; filename=`?
        // TODO cache
        return AbuResponses.create( uncheck( () -> Files.newInputStream( path ) ) )
                           .header( h -> h.contentType( guessMediaType( path.toString() ) ) );
    }
}
