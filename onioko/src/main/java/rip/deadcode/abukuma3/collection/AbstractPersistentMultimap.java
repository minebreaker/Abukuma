package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.ImMap;
import org.organicdesign.fp.collections.PersistentHashMap;
import org.organicdesign.fp.collections.PersistentVector;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.organicdesign.fp.StaticImports.vec;


public abstract class AbstractPersistentMultimap<K, V>
        implements PersistentMultimap<K, V> {

    private final ImMap<K, ImList<V>> delegate;

    protected AbstractPersistentMultimap() {
        this.delegate = PersistentHashMap.empty();
    }

    protected AbstractPersistentMultimap( Envelope<K, V> envelope ) {
        this.delegate = envelope.load;
    }

    @SuppressWarnings( "unchecked" )
    protected AbstractPersistentMultimap( Multimap<K, V> copy ) {
        this.delegate = copy.asMap().entrySet().stream().reduce(
                PersistentHashMap.empty(),
                ( acc, e ) -> acc.assoc( e.getKey(), PersistentVector.ofIter( e.getValue() ) ),
                // Should be safe to cast
                ( t, o ) -> (PersistentHashMap<K, ImList<V>>) PersistentHashMap.of( (Iterable) t.concat( o ) )
        );
    }

    protected static class Envelope<K, V> {
        private final ImMap<K, ImList<V>> load;

        private Envelope( ImMap<K, ImList<V>> load ) {
            this.load = load;
        }
    }

    protected abstract PersistentMultimap<K, V> constructor( Envelope<K, V> delegate );

    private PersistentMultimap<K, V> constructor( ImMap<K, ImList<V>> delegate ) {
        return constructor( new Envelope<>( delegate ) );
    }

    @Override public int size() {
        //noinspection SimplifyStreamApiCallChains  // ImMap deprecating #values()
        return delegate.entrySet().stream().mapToInt( e -> e.getValue().size() ).sum();
    }

    @Override public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override public boolean containsKey( @Nullable Object key ) {
        //noinspection SuspiciousMethodCalls
        return delegate.containsKey( key );
    }

    @Override public boolean containsValue( @Nullable Object value ) {
        return delegate.entrySet().stream().anyMatch( e -> contains( e.getValue(), value ) );
    }

    @SuppressWarnings( "SuspiciousMethodCalls" )
    @Override public boolean containsEntry( @Nullable Object key, @Nullable Object value ) {
        return delegate.containsKey( key ) && contains( delegate.get( key ), value );
    }

    @Override public Set<K> keySet() {
        return delegate.keySet();
    }

    @Override public Multiset<K> keys() {
        //noinspection ResultOfMethodCallIgnored  // Flase positive?
        return entries().stream()
                        .collect( groupingBy( Map.Entry::getKey ) )
                        .entrySet().stream()
                        .collect(
                                HashMultiset::create,
                                ( acc, e ) -> acc.add(
                                        e.getKey(),
                                        e.getValue().size()
                                ),
                                Multisets::sum
                        );
    }

    @Override public Collection<V> values() {
        return delegate.entrySet().stream().flatMap( e -> e.getValue().stream() ).collect( toList() );
    }

    @Override public Collection<Map.Entry<K, V>> entries() {
        return delegate.entrySet().stream()
                       .flatMap( e -> e.getValue().stream().map( v -> Maps.immutableEntry( e.getKey(), v ) ) )
                       .collect( toSet() );
    }

    @SuppressWarnings( "unchecked" )
    @Override public Map<K, Collection<V>> asMap() {
        return ( (Map<K, Collection<V>>) (Object) delegate );
    }

    @Override
    public V getValue( K key ) {
        checkNotNull( key );

        ImList<V> value = delegate.get( key );
        if ( value != null ) {
            // Never be an empty list.
            return value.get( 0 );
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<V> get( K key ) {
        checkNotNull( key );

        ImList<V> value = delegate.get( key );
        if ( delegate.containsKey( key ) ) {
            return value;
        } else {
            return PersistentVector.empty();
        }
    }

    @Override
    public Optional<V> mayGet( K key ) {
        checkNotNull( key );

        ImList<V> value = delegate.get( key );
        if ( value != null ) {
            // Never be an empty list.
            return Optional.of( value.get( 0 ) );
        } else {
            return Optional.empty();
        }
    }

    @Override
    public PersistentMultimap<K, V> add( K key, V value ) {
        checkNotNull( key );
        checkNotNull( value );

        if ( delegate.containsKey( key ) ) {
            return set( key, delegate.get( key ).append( value ) );

        } else {
            return set( key, value );
        }
    }

    @Override
    public PersistentMultimap<K, V> add( K key, Iterable<? extends V> values ) {
        checkNotNull( key );
        checkNotNull( values );

        if ( delegate.containsKey( key ) ) {
            return set( key, delegate.get( key ).concat( values ) );

        } else {
            return set( key, values );
        }
    }

    @Override
    public PersistentMultimap<K, V> set( K key, V value ) {
        checkNotNull( key );
        checkNotNull( value );

        return constructor( delegate.assoc( key, vec( value ) ) );
    }

    protected Envelope<K, V> setRaw( K key, V value ) {
        checkNotNull( key );
        checkNotNull( value );

        return new Envelope<>( delegate.assoc( key, vec( value ) ) );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public PersistentMultimap<K, V> set( K key, Iterable<? extends V> values ) {
        checkNotNull( key );
        checkNotNull( values );

        // Should be safe to cast
        return constructor( delegate.assoc( key, (ImList<V>) PersistentVector.ofIter( values ) ) );
    }

    @Override
    public PersistentMultimap<K, V> delete( K key ) {
        checkNotNull( key );

        if ( delegate.containsKey( key ) ) {
            return constructor( delegate.without( key ) );
        } else {
            throw new NoSuchElementException();
        }
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

    @Deprecated @Override public final List<V> removeAll( @Nullable Object key ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override
    public final List<V> replaceValues( @Nullable K key, @Nullable Iterable<? extends V> values ) {
        throw new UnsupportedOperationException();
    }

    private static boolean contains( ImList<?> list, Object value ) {
        for ( Object e : list ) {
            if ( Objects.equals( e, value ) ) {
                return true;
            }
        }
        return false;
    }

    @Override public ListMultimap<K, V> mutable() {
        return ArrayListMultimap.create( this );
    }
}
