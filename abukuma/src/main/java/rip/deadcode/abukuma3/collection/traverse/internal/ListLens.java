package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.traverse.GetterStreamable;
import rip.deadcode.abukuma3.collection.traverse.LensStreamable;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public final class ListLens<T> implements LensStreamable<List<T>, T> {

    private final int index;

    public ListLens( int index ) {
        this.index = index;
    }

    @Override public GetterStreamable<List<T>, T> getter() {
        return list -> list.stream();
    }

    @Override public T get( List<T> object ) {
        return object.get( index );
    }

    @Override public Stream<T> getAll( List<T> object ) {
        return object.stream();
    }

    @Override public Setter<List<T>, T> setter() {
        return ( list, element ) -> {
            List<T> copy = new ArrayList<>( list );
            copy.set( index, element );
            return copy;
        };
    }
}
