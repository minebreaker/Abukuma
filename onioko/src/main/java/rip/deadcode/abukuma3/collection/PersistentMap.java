package rip.deadcode.abukuma3.collection;

import java.util.Map;
import java.util.Optional;


public interface PersistentMap<K, V> extends Map<K, V> {

    public Optional<V> mayGet( K key );

    public PersistentMap<K, V> set( K key, V value );

    public PersistentMap<K, V> delete( K key );

    public PersistentMap<K, V> merge( Map<K, V> map );

    public Map<K, V> mutable();
}
