package rip.deadcode.abukuma3.collection;


import java.util.Set;


public interface PersistentSet<T> extends Set<T> {

    public PersistentSet<T> set( T value );

    public PersistentSet<T> merge( Iterable<? extends T> other );

    public Set<T> mutable();
}
