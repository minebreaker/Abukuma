package rip.deadcode.abukuma3.collection.traverse;

import java.util.function.Function;


@FunctionalInterface
public interface Getter<S, A> {

    public A get( S object );

    public default Function<S, A> f() {
        return this::get;
    }
}
