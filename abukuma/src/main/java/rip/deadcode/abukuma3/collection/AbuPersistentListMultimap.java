package rip.deadcode.abukuma3.collection;


import com.google.common.collect.Multimap;


public final class AbuPersistentListMultimap<K, V> extends AbstractPersistentListMultimap<K, V, AbuPersistentListMultimap<K, V>> {

    private AbuPersistentListMultimap() {
        super();
    }

    private AbuPersistentListMultimap( Envelope<K, V> delegate ) {
        super( delegate );
    }

    private AbuPersistentListMultimap( Multimap<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> AbuPersistentListMultimap<K, V> create() {
        return new AbuPersistentListMultimap<>();
    }

    public static <K, V> AbuPersistentListMultimap<K, V> create( Multimap<K, V> delegate ) {
        return new AbuPersistentListMultimap<>( delegate );
    }

    @Override
    protected AbuPersistentListMultimap<K, V> constructor( Envelope<K, V> delegate ) {
        return new AbuPersistentListMultimap<>( delegate );
    }
}
