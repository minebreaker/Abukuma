package rip.deadcode.abukuma3.collection;


public final class PersistentMapImpl<K, V> extends AbstractPersistentMap<K, V, PersistentMapImpl<K, V>> {

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
