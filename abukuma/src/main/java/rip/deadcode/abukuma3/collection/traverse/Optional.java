package rip.deadcode.abukuma3.collection.traverse;

public interface Optional<S, A> {

    public java.util.Optional<A> getOption( S object );

    public S set( S object, A element );
}
