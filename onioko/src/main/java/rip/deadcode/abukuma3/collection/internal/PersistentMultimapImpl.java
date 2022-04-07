package rip.deadcode.abukuma3.collection.internal;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;


public final class PersistentMultimapImpl<K, V>
        extends AbstractPersistentMultimap<K, V, PersistentMultimapImpl<K, V>> {

    public PersistentMultimapImpl() {
        super();
    }

    public PersistentMultimapImpl( Multimap<K, V> delegate ) {
        super( delegate );
    }

    public PersistentMultimapImpl( Envelope<K, V> delegate ) {
        super( delegate );
    }

    @Override
    protected PersistentMultimapImpl<K, V> constructor( Envelope<K, V> delegate ) {
        return new PersistentMultimapImpl<>( delegate );
    }
}
