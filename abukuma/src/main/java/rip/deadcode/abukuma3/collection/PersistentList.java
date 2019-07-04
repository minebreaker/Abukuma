package rip.deadcode.abukuma3.collection;

import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Pathable;
import rip.deadcode.abukuma3.collection.traverse.internal.PersistentListLens;

import java.util.List;
import java.util.Optional;


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
        return new PersistentListLens<>( Integer.parseInt( path ) );
    }
}
