package rip.deadcode.abukuma3.collection.internal;

import rip.deadcode.abukuma3.collection.Tuple2;


public final class Tuple2Impl<T0, T1> implements Tuple2<T0, T1> {

    private final T0 t0;
    private final T1 t1;

    public Tuple2Impl( T0 t0, T1 t1) {
        this.t0 = t0;
        this.t1 = t1;
    }

    @Override public T0 get0() {
        return t0;
    }

    @Override public T1 get1() {
        return t1;
    }

    @Override public T0 getKey() {
        return t0;
    }

    @Override public T1 getValue() {
        return t1;
    }
}
