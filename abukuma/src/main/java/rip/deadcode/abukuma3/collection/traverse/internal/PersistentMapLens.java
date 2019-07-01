package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;


public final class PersistentMapLens<K, V, T extends PersistentMap<K, V, T>>
        implements Lens<T, V> {

    private final K key;

    public PersistentMapLens( K key ) {
        this.key = key;
    }

    @Override public Getter<T, V> getter() {
        return map -> map.get( key );
    }

    @Override public Setter<T, V> setter() {
        return ( map, value ) -> map.set( key, value );
    }
}
