package rip.deadcode.abukuma3.collection;

import java.util.Map;
import java.util.Optional;


public interface PersistentMap<K, V, T extends PersistentMap<K, V, T>> extends Map<K, V> {

    public Optional<V> mayGet( K key );

    public T set( K key, V value );

    public T delete( K key );

    public T merge( Map<K, V> map );
}
