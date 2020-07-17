package rip.deadcode.abukuma3.collection;

import rip.deadcode.abukuma3.collection.internal.PersistentListImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentMultimapImpl;
import rip.deadcode.abukuma3.collection.internal.PersistentMapImpl;


public final class PersistentCollections {

    private PersistentCollections() {
        throw new Error();
    }

    public static <T> PersistentList<T> createList() {
        return PersistentListImpl.create();
    }

    public static <K, V> PersistentMap<K, V> createMap() {
        return PersistentMapImpl.create();
    }

    public static <K, V> PersistentMultimap<K, V> createMultimap() {
        return PersistentMultimapImpl.create();
    }
}
