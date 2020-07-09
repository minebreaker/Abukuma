package rip.deadcode.abukuma3.handler.internal;


import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.collection.AbuPersistentMap;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.handler.TypeBasedDispatcher;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;


public final class TypeBasedDispatcherImpl implements TypeBasedDispatcher {

    private final AbuPersistentMap<String, AbuHandler> handlers;
    @Nullable private final AbuHandler fallback;

    public TypeBasedDispatcherImpl( AbuPersistentMap<String, AbuHandler> handlers, @Nullable AbuHandler fallback ) {
        this.handlers = handlers;
        this.fallback = fallback;
    }

    @Override public AbuResponse handle( AbuExecutionContext context, AbuRequest request ) {

        String contentType = request.header().contentType();

        AbuHandler handler = handlers.get( contentType );
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

    @Override public TypeBasedDispatcher json( AbuHandler handler ) {
        return new TypeBasedDispatcherImpl( handlers.set( "application/json", handler ), fallback );
    }

    @Override public TypeBasedDispatcher xml( AbuHandler handler ) {
        // TODO what about text/xml?
        return new TypeBasedDispatcherImpl( handlers.set( "application/xml", handler ), fallback );
    }

    @Override public TypeBasedDispatcher text( AbuHandler handler ) {
        return new TypeBasedDispatcherImpl( handlers.set( "plain/text", handler ), fallback );
    }

    @Override public TypeBasedDispatcher fallback( AbuHandler handler ) {
        return new TypeBasedDispatcherImpl( handlers, handler );
    }
}
