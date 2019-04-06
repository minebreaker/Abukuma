package rip.deadcode.abukuma3.collection;


import com.google.common.collect.ForwardingListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;


public abstract class ForwardingPersistentListMultimap<K, V, T extends PersistentListMultimap<K, V, T>>
        extends ForwardingListMultimap<K, V>
        implements PersistentListMultimap<K, V, T> {

    protected ForwardingPersistentListMultimap() {}

    @Override
    protected abstract PersistentListMultimap<K, V, T> delegate();

    @Override public V getValue( K key ) {
        return delegate().getValue( key );
    }

    @Override public Optional<V> mayGet( K key ) {
        return delegate().mayGet( key );
    }

    @Override public T add( K key, V value ) {
        return delegate().add( key, value );
    }

    @Override public T add( K key, Iterable<? extends V> values ) {
        return delegate().add( key, values );
    }

    @Override public T set( K key, V value ) {
        return delegate().set( key, value );
    }

    @Override public T set( K key, Iterable<? extends V> values ) {
        return delegate().set( key, values );
    }

    @Override public T delete( K key ) {
        return delegate().delete( key );
    }

    @Deprecated @Override public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean put( @Nullable K key, @Nullable V value ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean putAll( @Nullable K key, @Nullable Iterable<? extends V> values ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean putAll( @Nullable Multimap<? extends K, ? extends V> multimap ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean remove( @Nullable Object key, @Nullable Object value ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override final public List<V> removeAll( @Nullable Object key ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override final public List<V> replaceValues( @Nullable K key, @Nullable Iterable<? extends V> values ) {
        throw new UnsupportedOperationException();
    }
}
