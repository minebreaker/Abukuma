package rip.deadcode.abukuma3.collection.traverse;

import java.util.Optional;

public interface Prism<S, A> {

    public Optional<A> getOption( S object );

    public S reverseGet( A element );
}
