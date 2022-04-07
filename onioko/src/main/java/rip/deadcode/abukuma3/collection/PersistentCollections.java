package rip.deadcode.abukuma3.collection;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.internal.PersistentListImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentMapImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentMultimapImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentSetImpl;
import rip.deadcode.abukuma3.collection.internal.Tuple2Impl;
import rip.deadcode.abukuma3.collection.internal.Tuple3Impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


public final class PersistentCollections {

    private PersistentCollections() {
        throw new Error();
    }

    public static <T> PersistentList<T> createList() {
        return PersistentListImpl.create();
    }

    public static <T> PersistentList<T> createList( T first ) {
        return PersistentListImpl.<T>create().addFirst( first );
    }

    @SafeVarargs
    public static <T> PersistentList<T> createList( T first, T... rest ) {
        return PersistentListImpl.<T>create().addFirst( first ).concat( Arrays.asList( rest ) );
    }

    public static <T> PersistentList<T> wrapList( List<T> copy ) {

        if ( copy instanceof PersistentList ) {
            return (PersistentList<T>) copy;
        }

        return PersistentListImpl.wrap( copy );
    }

    public static <T> PersistentSet<T> createSet() {
        return PersistentSetImpl.create();
    }

    public static <T> PersistentSet<T> createSet( T first ) {
        return PersistentSetImpl.<T>create().set( first );
    }

    @SafeVarargs
    public static <T> PersistentSet<T> createSet( T first, T... rest ) {
        return PersistentSetImpl.<T>create().set( first ).merge( Arrays.asList( rest ) );
    }

    public static <T> PersistentSet<T> wrapSet( Set<T> copy ) {
        return PersistentSetImpl.wrap( copy );
    }

    public static <K, V> PersistentMap<K, V> createMap() {
        return PersistentMapImpl.create();
    }

    public static <K, V> PersistentMap<K, V> createMap( K key, V value ) {
        return PersistentMapImpl.<K, V>create().set( key, value );
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

    public static <T0, T1> Tuple2<T0, T1> createTuple( T0 value0, T1 value1 ) {
        return new Tuple2Impl<>( value0, value1 );
    }

    public static <T0, T1, T2> Tuple3<T0, T1, T2> createTuple( T0 value0, T1 value1, T2 value2 ) {
        return new Tuple3Impl<>( value0, value1, value2 );
    }
}
