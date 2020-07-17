package rip.deadcode.abukuma3.collection.internal;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;


public final class PersistentMultimapImpl<K, V>
        extends AbstractPersistentMultimap<K, V> {

    private PersistentMultimapImpl() {
        super();
    }

    PersistentMultimapImpl( Envelope<K, V> delegate ) {
        super( delegate );
    }

    private PersistentMultimapImpl( Multimap<K, V> delegate ) {
        super( delegate );
    }

    public static <K, V> PersistentMultimapImpl<K, V> create() {
        return new PersistentMultimapImpl<>();
    }

    public static <K, V> PersistentMultimapImpl<K, V> create( Multimap<K, V> delegate ) {
        return new PersistentMultimapImpl<>( delegate );
    }

    @Override
    protected PersistentMultimapImpl<K, V> constructor( Envelope<K, V> delegate ) {
        return new PersistentMultimapImpl<>( delegate );
    }
}
