package rip.deadcode.abukuma3.collection;

import com.google.common.collect.Multimap;


public final class PersistentListMultimapImpl<K, V>
        extends AbstractPersistentListMultimap<K, V, PersistentListMultimapImpl<K, V>> {

    private PersistentListMultimapImpl() {
        super();
    }

    PersistentListMultimapImpl( Envelope<K, V> delegate ) {
        super( delegate );
    }

    private PersistentListMultimapImpl( Multimap<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> PersistentListMultimapImpl<K, V> create() {
        return new PersistentListMultimapImpl<>();
    }

    public static <K, V> PersistentListMultimapImpl<K, V> create( Multimap<K, V> delegate ) {
        return new PersistentListMultimapImpl<>( delegate );
    }

    @Override
    protected PersistentListMultimapImpl<K, V> constructor( Envelope<K, V> delegate ) {
        return new PersistentListMultimapImpl<>( delegate );
    }
}
