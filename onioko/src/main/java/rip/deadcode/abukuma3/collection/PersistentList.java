package rip.deadcode.abukuma3.collection;

import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Pathable;
import rip.deadcode.abukuma3.collection.traverse.internal.PersistentListLens;

import java.util.List;
import java.util.Optional;


public interface PersistentList<V, T extends PersistentList<V, T>> extends List<V>, Pathable<T, V> {

    public Optional<V> mayGet( int nth );

    public V first();

    public V last();

    public T addFirst( V value );

    public T addLast( V value );

    public T insert( int n, V value );

    public T replace( int n, V value );

    public T delete( int n );

    public T concat( Iterable<? extends V> list );

    public List<V> mutable();

    @Override public default Lens<T, V> lens( String path ) {
        return new PersistentListLens<>( Integer.parseInt( path ) );
    }
}
