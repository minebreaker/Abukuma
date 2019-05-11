package rip.deadcode.abukuma3.collection.traverse;

import java.util.HashMap;
import java.util.Map;


public class MapLens<K, V> implements Lens<Map<K, V>, V> {

    private final K key;

    public MapLens( K key ) {
        this.key = key;
    }

    @Override
    public V get( Map<K, V> object ) {
        return object.get( key );
    }

    @Override
    public Map<K, V> set( Map<K, V> object, V value ) {
        Map<K, V> temp = new HashMap<>( object );
        temp.put( key, value );
        return temp;
    }
}
