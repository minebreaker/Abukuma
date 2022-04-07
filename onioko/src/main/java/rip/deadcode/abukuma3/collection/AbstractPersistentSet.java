package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingSet;
import org.organicdesign.fp.collections.ImSet;
import org.organicdesign.fp.collections.PersistentHashSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public abstract class AbstractPersistentSet<T, U extends PersistentSetView<T, U>>
        extends ForwardingSet<T>
        implements PersistentSetView<T, U> {

    private final ImSet<T> delegate;

    protected AbstractPersistentSet() {
        this.delegate = PersistentHashSet.empty();
    }

    protected AbstractPersistentSet( Envelope<T> envelope ) {
        this.delegate = envelope.load;
    }

    protected AbstractPersistentSet( Iterable<T> copy ) {
        this.delegate = PersistentHashSet.of( copy );
    }

    protected AbstractPersistentSet( Iterator<T> iter ) {
        var s = PersistentHashSet.<T>emptyMutable();
        while ( iter.hasNext() ) {
            s.put( iter.next() );
        }
        this.delegate = s.immutable();
    }

    protected static final class Envelope<T> {
        private final ImSet<T> load;

        private Envelope( ImSet<T> load ) {
            this.load = load;
        }
    }

    @Override protected final Set<T> delegate() {
        return delegate;
    }

    protected abstract U constructor( Envelope<T> envelope );

    @Override public PersistentSet<T> set( T value ) {
        return constructor( new Envelope<>( delegate.put( value ) ) );
    }

    @Override public PersistentSet<T> merge( Iterable<? extends T> set ) {
        return constructor( new Envelope<>( delegate.union( set ) ) );
    }

    @Override public Set<T> mutable() {
        return new HashSet<>( delegate );
    }
}
