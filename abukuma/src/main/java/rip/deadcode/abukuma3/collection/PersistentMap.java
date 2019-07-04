package rip.deadcode.abukuma3.collection;

import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Pathable;
import rip.deadcode.abukuma3.collection.traverse.internal.PathedPersistentMapLens;

import java.util.Map;
import java.util.Optional;


public interface PersistentMap<K, V, T extends PersistentMap<K, V, T>>
        extends Map<K, V>, Pathable<T, V> {

    public Optional<V> mayGet( K key );

    public T set( K key, V value );

    public T delete( K key );

    public T merge( Map<K, V> map );

    @Override public default Lens<T, V> lens( String path ) {
        return new PathedPersistentMapLens<>( path );
    }
}
