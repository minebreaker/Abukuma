package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ListMultimap;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Pathable;
import rip.deadcode.abukuma3.collection.traverse.internal.PathedPersistentListMultimapLens;

import java.util.List;
import java.util.Optional;


public interface PersistentMultimap<K, V>
        extends ListMultimap<K, V>, Pathable<PersistentMultimap<K, V>, List<V>> {

    public V getValue( K key );

    public Optional<V> mayGet( K key );

    public PersistentMultimap<K, V> add( K key, V value );

    public PersistentMultimap<K, V> add( K key, Iterable<? extends V> values );

    public PersistentMultimap<K, V> set( K key, V value );

    public PersistentMultimap<K, V> set( K key, Iterable<? extends V> values );

    public PersistentMultimap<K, V> delete( K key );

    public ListMultimap<K, V> mutable();

    @Override public default Lens<PersistentMultimap<K, V>, List<V>> lens( String path ) {
        return new PathedPersistentListMultimapLens<>( path );
    }
}
