package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.List;


public final class PersistentListMultimapLens<K, V, T extends PersistentMultimap<K, V, T>>
        implements Lens<T, List<V>> {

    private K key;

    public PersistentListMultimapLens( K key ) {
        this.key = key;
    }

    @Override public Getter<T, List<V>> getter() {
        return multimap -> multimap.get( key );
    }

    @Override public Setter<T, List<V>> setter() {
        return ( multimap, values ) -> multimap.set( key, values );
    }
}
