package rip.deadcode.abukuma3.collection;


import com.google.common.collect.ImmutableList;

import java.util.List;


// TODO better name as a public interface?
public final class PersistentListImpl<V> extends AbstractPersistentList<V> {

    private PersistentListImpl() {
        super();
    }

    private PersistentListImpl( Envelope<V> envelope ) {
        super( envelope );
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

    @Override protected PersistentListImpl<V> constructor( Envelope<V> envelope ) {
        return new PersistentListImpl<>( envelope );
    }
}
