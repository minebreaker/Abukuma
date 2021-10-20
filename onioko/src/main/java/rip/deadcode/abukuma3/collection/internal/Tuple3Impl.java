package rip.deadcode.abukuma3.collection.internal;

import rip.deadcode.abukuma3.collection.Tuple3;


public final class Tuple3Impl<T0, T1, T2> implements Tuple3<T0, T1, T2> {

    private final T0 t0;
    private final T1 t1;
    private final T2 t2;

    public Tuple3Impl( T0 t0, T1 t1, T2 t2 ) {
        this.t0 = t0;
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override public T0 get0() {
        return t0;
    }

    @Override public T1 get1() {
        return t1;
    }

    @Override public T2 get2() {
        return t2;
    }
}
