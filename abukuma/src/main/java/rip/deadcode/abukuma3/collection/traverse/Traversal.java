package rip.deadcode.abukuma3.collection.traverse;

import java.util.function.Function;


public interface Traversal<S, A> {

    public S modify( Function<A, A> applicative, S object );
}
