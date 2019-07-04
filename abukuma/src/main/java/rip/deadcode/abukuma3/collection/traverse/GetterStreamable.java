package rip.deadcode.abukuma3.collection.traverse;

import java.util.stream.Stream;


public interface GetterStreamable<S, A> extends Getter<S, A> {

    public Stream<A> stream( S object );
}
