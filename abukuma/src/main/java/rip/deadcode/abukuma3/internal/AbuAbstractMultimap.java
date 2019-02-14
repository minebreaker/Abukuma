package rip.deadcode.abukuma3.internal;

import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbuAbstractMultimap<K, V, T extends AbuAbstractMultimap<K, V, T>> extends ForwardingMultimap<K, V> {

    protected abstract T constructor( Multimap<K, V> delegate );

    public T copy() {
        return constructor( ImmutableMultimap.copyOf( delegate() ) );
    }

    public V getValue( K key ) {
        return checkNotNull( get( key ).iterator().next() );
    }

    public Set<V> getValues( K key ) {
        return ImmutableSet.copyOf( delegate().get( key ) );
    }

    public Optional<V> mayGet( K key ) {
        return Optional.ofNullable( get( key ).iterator().next() );
    }

    public T set( K key, V value ) {
        Multimap<K, V> temp = HashMultimap.create();
        temp.putAll( delegate() );
        temp.removeAll( key );
        temp.put( key, value );
        return constructor( ImmutableMultimap.copyOf( temp ) );
    }

    public T set( K key, Collection<V> values ) {
        Multimap<K, V> temp = HashMultimap.create();
        temp.putAll( delegate() );
        temp.removeAll( key );
        temp.putAll( key, values );
        return constructor( ImmutableMultimap.copyOf( temp ) );
    }

    public T add( K key, V value ) {
        return constructor( ImmutableMultimap.<K, V>builder().putAll( delegate() ).put( key, value ).build() );
    }

    public T delete( K key ) {
        Multimap<K, V> temp = HashMultimap.create();
        temp.putAll( delegate() );
        temp.removeAll( key );
        return constructor( ImmutableMultimap.copyOf( temp ) );
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

    @Deprecated @Override final public Collection<V> removeAll( @Nullable Object key ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override final public Collection<V> replaceValues( @Nullable K key, @Nullable Iterable<? extends V> values ) {
        throw new UnsupportedOperationException();
    }
}
