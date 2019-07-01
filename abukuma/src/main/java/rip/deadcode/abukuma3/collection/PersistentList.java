package rip.deadcode.abukuma3.collection;

import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Parsers;
import rip.deadcode.abukuma3.collection.traverse.Pathable;
import rip.deadcode.abukuma3.collection.traverse.internal.PersistentListLens;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static com.google.common.base.Preconditions.checkState;


public interface PersistentList<T> extends List<T>, Pathable<PersistentList<T>, T> {

    public Optional<T> mayGet( int nth );

    public T first();

    public T last();

    public PersistentList<T> addFirst( T value );

    public PersistentList<T> addLast( T value );

    public PersistentList<T> insert( int n, T value );

    public PersistentList<T> replace( int n, T value );

    public PersistentList<T> delete( int n );

    public PersistentList<T> concat( Iterable<? extends T> list );

    @Override public default Lens<PersistentList<T>, T> lens( String path ) {
        OptionalInt index = Parsers.parseInt( path );
        checkState( index.isPresent() );
        return new PersistentListLens<>( index.getAsInt() );
    }
}
