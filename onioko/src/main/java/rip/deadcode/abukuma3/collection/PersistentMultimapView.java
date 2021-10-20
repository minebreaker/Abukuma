package rip.deadcode.abukuma3.collection;

public interface PersistentMultimapView<K, V, T extends PersistentMultimap<K, V>>
        extends PersistentMultimap<K, V> {

    @Override public T add( K key, V value );

    @Override public T add( K key, Iterable<? extends V> values );

    @Override public T set( K key, V value );

    @Override public T set( K key, Iterable<? extends V> values );

    @Override public T delete( K key );
}
