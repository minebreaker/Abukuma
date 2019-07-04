package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.ArrayList;
import java.util.List;


public final class ListLens<T> implements Lens<List<T>, T> {

    private final int index;

    public ListLens( int index ) {
        this.index = index;
    }

    @Override public Getter<List<T>, T> getter() {
        return object -> object.get( index );
    }

    @Override public Setter<List<T>, T> setter() {
        return ( object, element ) -> {
            List<T> copy = new ArrayList<>( object );
            copy.set( index, element );
            return copy;
        };
    }
}
