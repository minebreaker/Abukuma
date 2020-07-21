package rip.deadcode.abukuma3.collection;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.internal.PersistentListImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentMapImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentMultimapImpl;

import java.util.List;
import java.util.Map;


public final class PersistentCollections {

    private PersistentCollections() {
        throw new Error();
    }

    public static <T> PersistentList<T> createList() {
        return PersistentListImpl.create();
    }

    public static <T> PersistentList<T> createList( T first, T... rest ) {
        return PersistentListImpl.create( first, rest );
    }

    public static <T> PersistentList<T> wrapList( List<T> copy ) {

        if ( copy instanceof PersistentList ) {
            return (PersistentList<T>) copy;
        }

        return PersistentListImpl.wrap( copy );
    }

    public static <K, V> PersistentMap<K, V> createMap() {
        return PersistentMapImpl.create();
    }

    public static <K, V> PersistentMap<K, V> wrapMap( Map<K, V> copy ) {

        if ( copy instanceof PersistentMap ) {
            return (PersistentMap<K, V>) copy;
        }

        return PersistentMapImpl.wrap( copy );
    }

    public static <K, V> PersistentMultimap<K, V> createMultimap() {
        return PersistentMultimapImpl.create();
    }

    public static <K, V> PersistentMultimap<K, V> wrapMultimap( Multimap<K, V> copy ) {

        if ( copy instanceof PersistentMultimap ) {
            return (PersistentMultimap<K, V>) copy;
        }

        return PersistentMultimapImpl.wrap( copy );
    }

    // TODO add other factory methods
}
