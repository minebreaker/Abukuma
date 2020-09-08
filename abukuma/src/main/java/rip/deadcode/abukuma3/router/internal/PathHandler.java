package rip.deadcode.abukuma3.router.internal;


import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.utils.MimeDetector;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;

import java.nio.file.Files;
import java.nio.file.Path;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


// TODO remove this and create PathRenderer
public final class PathHandler implements Handler {

    private final Path path;

    public PathHandler( Path path ) {
        this.path = path;
    }

    @Override public Response handle( ExecutionContext context, Request request ) {
        // TODO file existence check
        // TODO may add `Content-Disposition: attachment; filename=`?
        // TODO cache
        MimeDetector mimeDetector = context.get( MimeDetector.class );

        return Responses.create( uncheck( () -> Files.newInputStream( path ) ) )
                        .header( h -> h.contentType( mimeDetector.detect( path.toString(), path ) ) );
    }
}
