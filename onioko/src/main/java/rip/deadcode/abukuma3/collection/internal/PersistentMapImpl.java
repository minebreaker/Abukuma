package rip.deadcode.abukuma3.collection.internal;


import rip.deadcode.abukuma3.collection.AbstractPersistentMap;

import java.util.Map;


public final class PersistentMapImpl<K, V>
        extends AbstractPersistentMap<K, V, PersistentMapImpl<K, V>> {

    public PersistentMapImpl() {
        super();
    }

    public PersistentMapImpl( Envelope<K, V> envelope ) {
        super( envelope );
    }

    public PersistentMapImpl( Map<K, V> envelope ) {
        super( envelope );
    }

    @Override
    protected PersistentMapImpl<K, V> constructor( Envelope<K, V> envelope ) {
        return new PersistentMapImpl<>( envelope );
    }
}
