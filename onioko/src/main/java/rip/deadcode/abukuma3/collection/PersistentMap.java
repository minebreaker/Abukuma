package rip.deadcode.abukuma3.collection;

import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Pathable;
import rip.deadcode.abukuma3.collection.traverse.internal.PathedPersistentMapLens;

import java.util.Map;
import java.util.Optional;


public interface PersistentMap<K, V>
        extends Map<K, V>, Pathable<PersistentMap<K, V>, V> {

    public Optional<V> mayGet( K key );

    public PersistentMap<K, V> set( K key, V value );

    public PersistentMap<K, V> delete( K key );

    public PersistentMap<K, V> merge( Map<K, V> map );

    public Map<K, V> mutable();

    @Override public default Lens<PersistentMap<K, V>, V> lens( String path ) {
        return new PathedPersistentMapLens<>( path );
    }
}
