package rip.deadcode.abukuma3.collection;

public interface PersistentListView<T, U extends PersistentList<T>>
        extends PersistentList<T> {

    @Override public U addFirst( T value );

    @Override public U addLast( T value );

    @Override public U insert( int n, T value );

    @Override public U replace( int n, T value );

    @Override public U delete( int n );

    @Override public U concat( Iterable<? extends T> list );
}
