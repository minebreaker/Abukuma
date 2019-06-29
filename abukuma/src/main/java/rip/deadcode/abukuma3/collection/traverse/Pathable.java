package rip.deadcode.abukuma3.collection.traverse;


public interface Pathable<S, A> {

    public Lens<S, A> lens();
}
