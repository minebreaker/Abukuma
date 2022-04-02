package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingList;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.PersistentVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class AbstractPersistentList<T, U extends PersistentListView<T, U>>
        extends ForwardingList<T>
        implements PersistentListView<T, U> {

    private final ImList<T> delegate;

    protected AbstractPersistentList() {
        this.delegate = PersistentVector.empty();
    }

    protected AbstractPersistentList( Envelope<T> envelope ) {
        this.delegate = envelope.load;
    }

    protected AbstractPersistentList( List<T> copy ) {
        this.delegate = PersistentVector.ofIter( copy );
    }

    @Override protected final List<T> delegate() {
        return delegate;
    }

    protected static final class Envelope<T> {
        private final ImList<T> load;

        private Envelope( ImList<T> load ) {
            this.load = load;
        }
    }

    /**
     * To encapsulate the internal data structure.
     */
    protected abstract U constructor( Envelope<T> envelope );

    @Override public Optional<T> mayGet( int nth ) {

        if ( nth < 0 || nth >= delegate.size() ) {
            return Optional.empty();
        }

        return Optional.ofNullable( delegate.get( nth ) );
    }

    @Override public T first() {
        if ( delegate.isEmpty() ) {
            throw new IndexOutOfBoundsException( "Empty list." );
        }
        return delegate.get( 0 );
    }

    @Override public T last() {
        if ( delegate.isEmpty() ) {
            throw new IndexOutOfBoundsException( "Empty list." );
        }
        return delegate.get( delegate.size() - 1 );
    }

    @Override public U addFirst( T value ) {
        checkNotNull( value );

        return constructor( new Envelope<>(
                PersistentVector.<T>emptyMutable().append( value ).concat( delegate ).immutable()
        ) );
    }

    @Override public U addLast( T value ) {
        checkNotNull( value );

        return constructor( new Envelope<>(
                delegate.append( value )
        ) );
    }

    @Override public U insert( int nth, T value ) {

        checkNotNull( value );

        if ( nth < 0 || nth > delegate.size() ) {
            throw new IndexOutOfBoundsException();
        }

        return constructor( new Envelope<>(
                PersistentVector.<T>emptyMutable()
                                .concat( delegate.subList( 0, nth ) )
                                .append( value )
                                .concat( delegate.subList( nth, delegate.size() ) )
                                .immutable()
        ) );
    }

    @Override public U replace( int nth, T value ) {

        checkNotNull( value );

        if ( nth < 0 || nth >= delegate.size() ) {
            throw new IndexOutOfBoundsException();
        }

        return constructor( new Envelope<>(
                delegate.replace( nth, value )
        ) );
    }

    @Override public U delete( int nth ) {

        if ( nth < 0 || nth >= delegate.size() ) {
            throw new IndexOutOfBoundsException();
        }

        return constructor( new Envelope<>(
                PersistentVector.<T>emptyMutable()
                                .concat( delegate.subList( 0, nth ) )
                                .concat( delegate.subList( nth + 1, delegate.size() ) )
                                .immutable()
        ) );
    }

    @Override public U concat( Iterable<? extends T> list ) {
        return constructor( new Envelope<>(
                delegate.concat( list )
        ) );
    }

    @Override public List<T> mutable() {
        return new ArrayList<>( this );
    }
}
