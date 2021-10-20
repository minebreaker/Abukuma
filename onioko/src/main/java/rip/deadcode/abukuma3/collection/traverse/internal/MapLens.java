package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.HashMap;
import java.util.Map;


public class MapLens<K, V> implements Lens<Map<K, V>, V> {

    private final K key;

    public MapLens( K key ) {
        this.key = key;
    }

    public static <V> Lens<Map<String, V>, V> create( String key ) {
        return new MapLens<>( key );
    }

    @Override public Getter<Map<K, V>, V> getter() {
        return map -> map.get( key );
    }

    @Override public Setter<Map<K, V>, V> setter() {
        return ( map, value ) -> {
            Map<K, V> temp = new HashMap<>( map );
            temp.put( key, value );
            return temp;
        };
    }
}
