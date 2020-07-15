package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;


public final class PersistentMapLens<K, V> implements Lens<PersistentMap<K, V>, V> {

    private final K key;

    public PersistentMapLens( K key ) {
        this.key = key;
    }

    @Override public Getter<PersistentMap<K, V>, V> getter() {
        return map -> map.get( key );
    }

    @Override public Setter<PersistentMap<K, V>, V> setter() {
        return ( map, value ) -> map.set( key, value );
    }
}
