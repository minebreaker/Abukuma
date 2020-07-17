package rip.deadcode.abukuma3.handler;


import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.handler.internal.TypeBasedDispatcherImpl;


public final class Handlers {

    private Handlers() {
        throw new Error();
    }

    public static TypeBasedDispatcher byContentType() {
        return new TypeBasedDispatcherImpl( PersistentCollections.createMap(), null );
    }
}
