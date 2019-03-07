package rip.deadcode.abukuma3.internal.collection;

import com.google.common.collect.Multimap;


public interface PersistentListMultimap<K, V, T extends PersistentListMultimap<K, V, T>> extends Multimap<K, V> {

    public V getValue( K key );

    public T set( K key, V value );

    public T add( K key, V value );

    public T delete( K key );
}
