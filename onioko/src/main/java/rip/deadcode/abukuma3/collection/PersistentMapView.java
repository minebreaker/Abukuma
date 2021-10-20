package rip.deadcode.abukuma3.collection;

import java.util.Map;


public interface PersistentMapView<K, V, T extends PersistentMap<K, V>>
        extends PersistentMap<K, V> {

    @Override public T set( K key, V value );

    @Override public T delete( K key );

    @Override public T merge( Map<K, V> map );
}
