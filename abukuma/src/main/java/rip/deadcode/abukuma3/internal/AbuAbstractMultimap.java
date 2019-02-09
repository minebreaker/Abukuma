package rip.deadcode.abukuma3.internal;

import com.google.common.collect.*;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbuAbstractMultimap<T extends AbuAbstractMultimap> extends ForwardingMultimap<String, String> {

    public abstract T constructor( Multimap<String, String> delegate );

    public T copy() {
        return constructor( ImmutableMultimap.copyOf( delegate() ) );
    }

    public String getValue( String key ) {
        return checkNotNull( get( key ).iterator().next() );
    }

    public Set<String> getValues( String key ) {
        return ImmutableSet.copyOf( delegate().get( key ) );
    }

    public Optional<String> mayGet( String key ) {
        return Optional.ofNullable( get( key ).iterator().next() );
    }

    public T set( String key, String value ) {
        Multimap<String, String> temp = HashMultimap.create();
        temp.putAll( delegate() );
        temp.removeAll( key );
        temp.put( key, value );
        return constructor( ImmutableMultimap.copyOf( temp ) );
    }

    public T set( String key, Collection<String> values ) {
        Multimap<String, String> temp = HashMultimap.create();
        temp.putAll( delegate() );
        temp.removeAll( key );
        temp.putAll( key, values );
        return constructor( ImmutableMultimap.copyOf( temp ) );
    }

    public T add( String key, String value ) {
        return constructor( ImmutableMultimap.<String, String>builder().putAll( delegate() ).put( key, value ).build() );
    }

    public T delete( String key ) {
        Multimap<String, String> temp = HashMultimap.create();
        temp.putAll( delegate() );
        temp.removeAll( key );
        return constructor( ImmutableMultimap.copyOf( temp ) );
    }

    @Deprecated @Override public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean put( @Nullable String key, @Nullable String value ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean putAll( @Nullable String key, @Nullable Iterable<? extends String> values ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean putAll( @Nullable Multimap<? extends String, ? extends String> multimap ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override public final boolean remove( @Nullable Object key, @Nullable Object value ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override final public Collection<String> removeAll( @Nullable Object key ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override final public Collection<String> replaceValues( @Nullable String key, @Nullable Iterable<? extends String> values ) {
        throw new UnsupportedOperationException();
    }
}
