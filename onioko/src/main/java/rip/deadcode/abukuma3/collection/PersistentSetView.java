package rip.deadcode.abukuma3.collection;

public interface PersistentSetView<T, U extends PersistentSet<T>>
        extends PersistentSet<T> {

    @Override PersistentSet<T> set( T value );

    @Override PersistentSet<T> merge( Iterable<? extends T> set );
}
