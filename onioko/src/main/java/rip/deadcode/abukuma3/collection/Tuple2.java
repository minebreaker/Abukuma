package rip.deadcode.abukuma3.collection;

import java.util.Map;


public interface Tuple2<T0, T1> extends Map.Entry<T0, T1> {

    public T0 get0();

    public T1 get1();

    @Override public default T1 setValue( T1 value ) {
        throw new UnsupportedOperationException();
    }
}
