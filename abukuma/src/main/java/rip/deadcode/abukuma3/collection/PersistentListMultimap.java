package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ListMultimap;

import java.util.Optional;


public interface PersistentListMultimap<K, V, T extends PersistentListMultimap<K, V, T>> extends ListMultimap<K, V> {

    public V getValue( K key );

    public Optional<V> mayGet( K key );

    public T add( K key, V value );

    public T add( K key, Iterable<? extends V> values );

    public T set( K key, V value );

    public T set( K key, Iterable<? extends V> values );

    public T delete( K key );
}
