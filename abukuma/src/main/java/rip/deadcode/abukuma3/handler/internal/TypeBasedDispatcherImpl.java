package rip.deadcode.abukuma3.handler.internal;


import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentMapImpl;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.handler.TypeBasedDispatcher;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;


public final class TypeBasedDispatcherImpl implements TypeBasedDispatcher {

    private final PersistentMapImpl<String, Handler> handlers;
    @Nullable private final Handler fallback;

    public TypeBasedDispatcherImpl( PersistentMapImpl<String, Handler> handlers, @Nullable Handler fallback ) {
        this.handlers = handlers;
        this.fallback = fallback;
    }

    @Override public Response handle( ExecutionContext context, Request request ) {

        String contentType = request.header().contentType();

        Handler handler = handlers.get( contentType );
        if ( handler == null ) {
            if ( fallback == null ) {
                throw new RuntimeException(
                        String.format( "A fallback handler is not specified for the content type \"%s\"", contentType )
                );
            }

            return fallback.handle( context, request );
        }

        return handler.handle( context, request );
    }

    @Override public TypeBasedDispatcher json( Handler handler ) {
        return new TypeBasedDispatcherImpl( handlers.set( "application/json", handler ), fallback );
    }

    @Override public TypeBasedDispatcher xml( Handler handler ) {
        // TODO what about text/xml?
        return new TypeBasedDispatcherImpl( handlers.set( "application/xml", handler ), fallback );
    }

    @Override public TypeBasedDispatcher text( Handler handler ) {
        return new TypeBasedDispatcherImpl( handlers.set( "plain/text", handler ), fallback );
    }

    @Override public TypeBasedDispatcher fallback( Handler handler ) {
        return new TypeBasedDispatcherImpl( handlers, handler );
    }
}
