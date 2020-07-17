package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.List;


public final class PersistentListMultimapLens<K, V>
        implements Lens<PersistentMultimap<K, V>, List<V>> {

    private K key;

    public PersistentListMultimapLens( K key ) {
        this.key = key;
    }

    @Override public Getter<PersistentMultimap<K, V>, List<V>> getter() {
        return multimap -> multimap.get( key );
    }

    @Override public Setter<PersistentMultimap<K, V>, List<V>> setter() {
        return ( multimap, values ) -> multimap.set( key, values );
    }
}
