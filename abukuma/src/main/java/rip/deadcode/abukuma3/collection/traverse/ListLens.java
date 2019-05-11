package rip.deadcode.abukuma3.collection.traverse;

import java.util.ArrayList;
import java.util.List;


public final class ListLens<T> implements Lens<List<T>, T> {

    private final int index;

    public ListLens( int index ) {
        this.index = index;
    }

    @Override public T get( List<T> object ) {
        return object.get( index );
    }

    @Override public List<T> set( List<T> object, T element ) {
        List<T> copy = new ArrayList<>( object );
        copy.set( index, element );
        return copy;
    }
}
