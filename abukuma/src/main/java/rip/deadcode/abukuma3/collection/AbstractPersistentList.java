package rip.deadcode.abukuma3.collection;

import com.google.common.collect.ForwardingList;
import org.organicdesign.fp.collections.ImList;
import org.organicdesign.fp.collections.PersistentVector;

import java.util.List;
import java.util.Optional;

import static org.organicdesign.fp.StaticImports.vec;


public abstract class AbstractPersistentList<T, R extends AbstractPersistentList<T, R>>
        extends ForwardingList<T> implements PersistentList<T, R> {

    private final ImList<T> delegate;

    protected AbstractPersistentList() {
        this.delegate = PersistentVector.empty();
    }

    protected AbstractPersistentList( Envelope<T> delegate ) {
        this.delegate = delegate.load;
    }

    @Override
    protected List<T> delegate() {
        return delegate;
    }

    protected static final class Envelope<T> {
        private final ImList<T> load;

        public Envelope( ImList<T> load ) {
            this.load = load;
        }
    }

    protected abstract R constructor( Envelope<T> delegate );

    private R constructor( ImList<T> delegate ) {
        return constructor( new Envelope<>( delegate ) );
    }

    @Override
    public Optional<T> first() {
        return Optional.ofNullable( delegate.get( 0 ) );
    }

    @Override
    public Optional<T> last() {
        return Optional.ofNullable( delegate.get( delegate.size() - 1 ) );
    }

    @Override
    public R addFirst( T value ) {
        return constructor( vec( value ).concat( delegate ) );
    }

    @Override
    public R addLast( T value ) {
        return constructor( delegate.append( value ) );
    }

    @Override
    public R concat( List<T> list ) {
        return constructor( delegate.concat( list ) );
    }
}
