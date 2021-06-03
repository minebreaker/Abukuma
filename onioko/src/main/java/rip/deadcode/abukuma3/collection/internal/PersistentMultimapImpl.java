package rip.deadcode.abukuma3.collection.internal;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;


public final class PersistentMultimapImpl<K, V>
        extends AbstractPersistentMultimap<K, V, PersistentMultimapImpl<K, V>> {

    private PersistentMultimapImpl() {
        super();
    }

    private PersistentMultimapImpl( Multimap<K, V> delegate ) {
        super( delegate );
    }

    private PersistentMultimapImpl( Envelope<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> PersistentMultimapImpl<K, V> create() {
        return new PersistentMultimapImpl<>();
    }

    public static <K, V> PersistentMultimapImpl<K, V> wrap( Multimap<K, V> delegate ) {
        return new PersistentMultimapImpl<>( delegate );
    }

    @Override
    protected PersistentMultimapImpl<K, V> constructor( Envelope<K, V> delegate ) {
        return new PersistentMultimapImpl<>( delegate );
    }
}
