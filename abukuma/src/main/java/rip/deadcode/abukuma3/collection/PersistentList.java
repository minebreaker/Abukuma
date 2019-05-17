package rip.deadcode.abukuma3.collection;

import java.util.List;
import java.util.Optional;


public interface PersistentList<T> extends List<T> {

    public Optional<T> mayGet( int nth );

    public T first();

    public T last();

    public PersistentList<T> addFirst( T value );

    public PersistentList<T> addLast( T value );

    public PersistentList<T> concat( Iterable<? extends T> list );
}
