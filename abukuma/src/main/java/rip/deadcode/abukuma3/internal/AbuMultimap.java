package rip.deadcode.abukuma3.internal;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Set;

public abstract class AbuMultimap extends ForwardingMultimap<String, String> {

    public String getValue( String key ) {
        return delegate().get( key ).iterator().next();
    }

    public Set<String> getValues( String key ) {
        return ImmutableSet.copyOf( delegate().get( key ) );
    }

    public abstract AbuMultimap copy();

    public abstract AbuMultimap set( String key, String value );

    public abstract AbuMultimap set( String key, Iterable<String> values );

    public abstract AbuMultimap add( String key, String value );

    public abstract AbuMultimap delete( String key );

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
