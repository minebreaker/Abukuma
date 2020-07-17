package rip.deadcode.abukuma3.collection.internal;


import rip.deadcode.abukuma3.collection.AbstractPersistentMap;


public final class PersistentMapImpl<K, V> extends AbstractPersistentMap<K, V> {

    private PersistentMapImpl() {
        super();
    }

    private PersistentMapImpl( Envelope<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> PersistentMapImpl<K, V> create() {
        return new PersistentMapImpl<>();
    }

    @Override
    protected PersistentMapImpl<K, V> constructor( Envelope<K, V> delegate ) {
        return new PersistentMapImpl<>( delegate );
    }
}
