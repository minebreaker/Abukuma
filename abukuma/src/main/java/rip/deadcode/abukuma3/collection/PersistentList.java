package rip.deadcode.abukuma3.collection;

import java.util.List;
import java.util.Optional;


public interface PersistentList<T, R extends PersistentList<T, R>> extends List<T> {

    public Optional<T> first();

    public Optional<T> last();

    public R addFirst( T value );

    public R addLast( T value );

    public R concat( List<T> list );
}
