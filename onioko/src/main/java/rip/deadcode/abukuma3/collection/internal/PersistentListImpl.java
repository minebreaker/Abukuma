package rip.deadcode.abukuma3.collection.internal;


import rip.deadcode.abukuma3.collection.AbstractPersistentList;
import rip.deadcode.abukuma3.collection.PersistentList;

import java.util.List;


public final class PersistentListImpl<T>
        extends AbstractPersistentList<T, PersistentListImpl<T>> {

    private PersistentListImpl() {
        super();
    }

    private PersistentListImpl( Envelope<T> envelope ) {
        super( envelope );
    }

    private PersistentListImpl(List<T> delegate) {
        super(delegate);
    }

    @Override protected PersistentListImpl<T> constructor( Envelope<T> delegate ) {
        return new PersistentListImpl<>( delegate );
    }

    public static <V> PersistentListImpl<V> create() {
        return new PersistentListImpl<>();
    }

    public static <V> PersistentList<V> wrap( List<V> list ) {
        return new PersistentListImpl<V>( list );
    }
}
