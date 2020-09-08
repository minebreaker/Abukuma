package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.internal.utils.Resources;
import rip.deadcode.abukuma3.utils.MimeDetector;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;


public final class ResourceHandler implements Handler {

    private final String path;

    public ResourceHandler( String path ) {
        this.path = path;
    }

    @Override public Response handle( ExecutionContext context, Request request ) {
        // TODO may add `Content-Disposition: attachment; filename=`?
        // TODO cache
        MimeDetector mimeDetector = context.get( MimeDetector.class );

        return Responses.create( Resources.grabResource( path ) )
                        .header( h -> h.contentType( mimeDetector.detect( path ) ) );
    }
}
