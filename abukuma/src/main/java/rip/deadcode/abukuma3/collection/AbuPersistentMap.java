package rip.deadcode.abukuma3.collection;


public final class AbuPersistentMap<K, V> extends AbstractPersistentMap<K, V, AbuPersistentMap<K, V>> {

    private AbuPersistentMap() {
        super();
    }

    private AbuPersistentMap( Envelope<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> AbuPersistentMap<K, V> create() {
        return new AbuPersistentMap<>();
    }

    @Override
    protected AbuPersistentMap<K, V> constructor( Envelope<K, V> delegate ) {
        return new AbuPersistentMap<>( delegate );
    }
}
