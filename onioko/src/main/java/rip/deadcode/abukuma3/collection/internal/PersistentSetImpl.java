package rip.deadcode.abukuma3.collection.internal;

import rip.deadcode.abukuma3.collection.AbstractPersistentSet;

import java.util.Iterator;


public final class PersistentSetImpl<T>
        extends AbstractPersistentSet<T, PersistentSetImpl<T>> {

    public PersistentSetImpl() {
        super();
    }

    public PersistentSetImpl( Envelope<T> envelope ) {
        super( envelope );
    }

    public PersistentSetImpl( Iterable<T> envelope ) {
        super( envelope );
    }

    public PersistentSetImpl( Iterator<T> copy ) {
        super( copy );
    }

    @Override protected PersistentSetImpl<T> constructor( Envelope<T> envelope ) {
        return new PersistentSetImpl<>( envelope );
    }
}
