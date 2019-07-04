package rip.deadcode.abukuma3.collection.traverse;


import java.util.function.BiFunction;


@FunctionalInterface
public interface Setter<S, A> {

    public S set( S object, A value );

    public default BiFunction<S, A, S> f() {
        return this::set;
    }
}
