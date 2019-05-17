package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingList;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.PersistentVector;

import java.util.List;
import java.util.Optional;


public abstract class AbstractPersistentList<T> extends ForwardingList<T> implements PersistentList<T> {

    private final ImList<T> delegate;

    protected AbstractPersistentList() {
        this.delegate = PersistentVector.empty();
    }

    protected AbstractPersistentList( Envelope<T> envelope ) {
        this.delegate = envelope.load;
    }

    @Override protected List<T> delegate() {
        return delegate;
    }

    protected static final class Envelope<T> {
        private final ImList<T> load;

        private Envelope( ImList<T> load ) {
            this.load = load;
        }
    }

    protected abstract PersistentList<T> constructor( Envelope<T> envelope );

    @Override public Optional<T> mayGet( int nth ) {
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

    @Override public PersistentList<T> addFirst( T value ) {
        return constructor( new Envelope<>(
                PersistentVector.<T>emptyMutable().append( value ).concat( delegate ).immutable()
        ) );
    }

    @Override public PersistentList<T> addLast( T value ) {
        return constructor( new Envelope<>(
                delegate.append( value )
        ) );
    }

    @Override public PersistentList<T> concat( Iterable<? extends T> list ) {
        return constructor( new Envelope<>(
                delegate.concat( list )
        ) );
    }
}
