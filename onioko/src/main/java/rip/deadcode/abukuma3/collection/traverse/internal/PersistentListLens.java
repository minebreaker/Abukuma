package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;


public final class PersistentListLens<T> implements Lens<PersistentList<T>, T> {

    private final int index;

    public PersistentListLens( int index ) {
        this.index = index;
    }

    @Override public Getter<PersistentList<T>, T> getter() {
        return list -> list.get( index );
    }

    @Override public Setter<PersistentList<T>, T> setter() {
        return ( list, value ) -> list.replace( index, value );
    }
}
