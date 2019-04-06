package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.ImMap;
import org.organicdesign.fp.collections.PersistentHashMap;
import org.organicdesign.fp.collections.PersistentVector;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.organicdesign.fp.StaticImports.vec;


public abstract class AbstractPersistentListMultimap<K, V, T extends PersistentListMultimap<K, V, T>>
        extends ForwardingListMultimap<K, V>
        implements PersistentListMultimap<K, V, T> {

    private final ImMap<K, ImList<V>> delegate;
    private final AtomicReference<ListMultimap<K, V>> view = new AtomicReference<>();

    protected AbstractPersistentListMultimap() {
        this.delegate = PersistentHashMap.empty();
    }

    protected AbstractPersistentListMultimap( Envelope<K, V> envelope ) {
        this.delegate = envelope.load;
    }

    protected AbstractPersistentListMultimap( Multimap<K, V> copy ) {
        this.delegate = copy.asMap().entrySet().stream().reduce(
                PersistentHashMap.empty(),
                ( acc, e ) -> acc.assoc( e.getKey(), PersistentVector.ofIter( e.getValue() ) ),
                ( t, o ) -> (PersistentHashMap<K, ImList<V>>) PersistentHashMap.of( (Iterable) t.concat( o ) )
        );
    }

    protected static class Envelope<K, V> {
        private final ImMap<K, ImList<V>> load;

        private Envelope( ImMap<K, ImList<V>> load ) {
            this.load = load;
        }
    }

    protected abstract T constructor( Envelope<K, V> delegate );

    private T constructor( ImMap<K, ImList<V>> delegate ) {
        return constructor( new Envelope<>( delegate ) );
    }

    @Override protected final ListMultimap<K, V> delegate() {

        ListMultimap<K, V> current = view.get();

        if ( current == null ) {
            ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
            for ( Map.Entry<K, ImList<V>> e : delegate ) {
                builder.putAll( e.getKey(), e.getValue() );
            }
            ListMultimap<K, V> newInstance = builder.build();

            if ( view.compareAndSet( null, newInstance ) ) {
                return newInstance;
            } else {
                return delegate();
            }

        } else {
            return current;
        }
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
            throw new NoSuchElementException();
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
    public T add( K key, V value ) {
        checkNotNull( key );
        checkNotNull( value );

        if ( delegate.containsKey( key ) ) {
            return set( key, delegate.get( key ).append( value ) );

        } else {
            return set( key, value );
        }
    }

    @Override
    public T add( K key, Iterable<? extends V> values ) {
        checkNotNull( key );
        checkNotNull( values );

        if ( delegate.containsKey( key ) ) {
            return set( key, delegate.get( key ).concat( values ) );

        } else {
            return set( key, values );
        }
    }

    @Override
    public T set( K key, V value ) {
        checkNotNull( key );
        checkNotNull( value );

        return constructor( delegate.assoc( key, vec( value ) ) );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public T set( K key, Iterable<? extends V> values ) {
        checkNotNull( key );
        checkNotNull( values );

        // Should be safe to cast
        return constructor( delegate.assoc( key, (ImList<V>) PersistentVector.ofIter( values ) ) );
    }

    @Override
    public T delete( K key ) {
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

    @Deprecated @Override final public List<V> removeAll( @Nullable Object key ) {
        throw new UnsupportedOperationException();
    }

    @Deprecated @Override final public List<V> replaceValues( @Nullable K key, @Nullable Iterable<? extends V> values ) {
        throw new UnsupportedOperationException();
    }
}
