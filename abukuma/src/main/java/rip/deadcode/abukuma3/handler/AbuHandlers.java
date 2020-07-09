package rip.deadcode.abukuma3.handler;


import rip.deadcode.abukuma3.collection.AbuPersistentMap;
import rip.deadcode.abukuma3.handler.internal.TypeBasedDispatcherImpl;


public final class AbuHandlers {

    private AbuHandlers() {
        throw new Error();
    }

    public static TypeBasedDispatcher byContentType() {
        return new TypeBasedDispatcherImpl( AbuPersistentMap.create(), null );
    }
}
