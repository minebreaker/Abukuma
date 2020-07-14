package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingList;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.PersistentVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbstractPersistentList<V, T extends AbstractPersistentList<V, T>>
        extends ForwardingList<V>
        implements PersistentList<V, T> {

    private final ImList<V> delegate;

    protected AbstractPersistentList() {
        this.delegate = PersistentVector.empty();
    }

    protected AbstractPersistentList( Envelope<V> envelope ) {
        this.delegate = envelope.load;
    }

    @Override protected final List<V> delegate() {
        return delegate;
    }

    protected static final class Envelope<T> {
        private final ImList<T> load;

        private Envelope( ImList<T> load ) {
            this.load = load;
        }
    }

    protected abstract T constructor( Envelope<V> envelope );

    @Override public Optional<V> mayGet( int nth ) {

        if ( nth < 0 || nth >= delegate.size() ) {
            return Optional.empty();
        }

        return Optional.ofNullable( delegate.get( nth ) );
    }

    @Override public V first() {
        if ( delegate.isEmpty() ) {
            throw new IndexOutOfBoundsException( "Empty list." );
        }
        return delegate.get( 0 );
    }

    @Override public V last() {
        if ( delegate.isEmpty() ) {
            throw new IndexOutOfBoundsException( "Empty list." );
        }
        return delegate.get( delegate.size() - 1 );
    }

    @Override public T addFirst( V value ) {
        checkNotNull( value );

        return constructor( new Envelope<>(
                PersistentVector.<V>emptyMutable().append( value ).concat( delegate ).immutable()
        ) );
    }

    @Override public T addLast( V value ) {
        checkNotNull( value );

        return constructor( new Envelope<>(
                delegate.append( value )
        ) );
    }

    @Override public T insert( int nth, V value ) {

        checkNotNull( value );

        if ( nth < 0 || nth > delegate.size() ) {
            throw new IndexOutOfBoundsException();
        }

        return constructor( new Envelope<>(
                PersistentVector.<V>emptyMutable()
                        .concat( delegate.subList( 0, nth ) )
                        .append( value )
                        .concat( delegate.subList( nth, delegate.size() ) )
                        .immutable()
        ) );
    }

    @Override public T replace( int nth, V value ) {

        checkNotNull( value );

        if ( nth < 0 || nth >= delegate.size() ) {
            throw new IndexOutOfBoundsException();
        }

        return constructor( new Envelope<>(
                delegate.replace( nth, value )
        ) );
    }

    @Override public T delete( int nth ) {

        if ( nth < 0 || nth >= delegate.size() ) {
            throw new IndexOutOfBoundsException();
        }

        return constructor( new Envelope<>(
                PersistentVector.<V>emptyMutable()
                        .concat( delegate.subList( 0, nth ) )
                        .concat( delegate.subList( nth + 1, delegate.size() ) )
                        .immutable()
        ) );
    }

    @Override public T concat( Iterable<? extends V> list ) {
        return constructor( new Envelope<>(
                delegate.concat( list )
        ) );
    }

    @Override public List<V> mutable() {
        return new ArrayList<>( this );
    }
}
