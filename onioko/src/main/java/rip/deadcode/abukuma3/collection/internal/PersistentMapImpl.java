package rip.deadcode.abukuma3.collection.internal;


import rip.deadcode.abukuma3.collection.AbstractPersistentMap;

import java.util.Map;


public final class PersistentMapImpl<K, V>
        extends AbstractPersistentMap<K, V, PersistentMapImpl<K, V>> {

    private PersistentMapImpl() {
        super();
    }

    private PersistentMapImpl( Map<K, V> delegate ) {
        super( delegate );
    }

    private PersistentMapImpl( Envelope<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> PersistentMapImpl<K, V> create() {
        return new PersistentMapImpl<>();
    }

    public static <K, V> PersistentMapImpl<K, V> wrap( Map<K, V> delegate ) {
        return new PersistentMapImpl<>( delegate );
    }

    @Override
    protected PersistentMapImpl<K, V> constructor( Envelope<K, V> delegate ) {
        return new PersistentMapImpl<>( delegate );
    }
}
