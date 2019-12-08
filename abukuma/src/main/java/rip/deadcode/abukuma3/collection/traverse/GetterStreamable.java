package rip.deadcode.abukuma3.collection.traverse;

import java.util.NoSuchElementException;
import java.util.stream.Stream;


@FunctionalInterface
public interface GetterStreamable<S, A> extends Getter<S, A> {

    public Stream<A> stream( S object );

    @Override default A get( S object ) {
        return stream( object ).findFirst().orElseThrow( NoSuchElementException::new );
    }
}
