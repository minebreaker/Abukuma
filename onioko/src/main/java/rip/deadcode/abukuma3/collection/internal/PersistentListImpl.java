package rip.deadcode.abukuma3.collection.internal;


import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.collection.AbstractPersistentList;
import rip.deadcode.abukuma3.collection.PersistentList;

import java.util.List;


// TODO better name as a public interface?
public final class PersistentListImpl<T>
        extends AbstractPersistentList<T, PersistentListImpl<T>> {

    private PersistentListImpl() {
        super();
    }

    private PersistentListImpl( Envelope<T> envelope ) {
        super( envelope );
    }

    @Override protected PersistentListImpl<T> constructor( Envelope<T> delegate ) {
        return new PersistentListImpl<>( delegate );
    }

    public static <V> PersistentListImpl<V> create() {
        return new PersistentListImpl<>();
    }

    @SafeVarargs
    public static <V> PersistentList<V> create( V first, V... rest ) {
        return new PersistentListImpl<V>().addLast( first ).concat( ImmutableList.copyOf( rest ) );
    }

    public static <V> PersistentList<V> wrap( List<V> list ) {
        return new PersistentListImpl<V>().concat( list );
    }
}
