package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

import java.util.Optional;


public interface PersistentMultimap<K, V> extends ListMultimap<K, V> {

    // TODO: how about getSingle/getFirst?
    public V getValue( K key );

    public Optional<V> mayGet( K key );

    public PersistentMultimap<K, V> add( K key, V value );

    public PersistentMultimap<K, V> add( K key, Iterable<? extends V> values );

    public PersistentMultimap<K, V> set( K key, V value );

    public PersistentMultimap<K, V> set( K key, Iterable<? extends V> values );

    public PersistentMultimap<K, V> delete( K key );

    public PersistentMultimap<K, V> merge( Multimap<K, V> multimap );

    public ListMultimap<K, V> mutable();
}
