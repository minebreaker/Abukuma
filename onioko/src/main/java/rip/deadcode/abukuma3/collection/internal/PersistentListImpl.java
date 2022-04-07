package rip.deadcode.abukuma3.collection.internal;


import rip.deadcode.abukuma3.collection.AbstractPersistentList;

import java.util.Iterator;


public final class PersistentListImpl<T>
        extends AbstractPersistentList<T, PersistentListImpl<T>> {

    public PersistentListImpl() {
        super();
    }

    public PersistentListImpl( Envelope<T> envelope ) {
        super( envelope );
    }

    public PersistentListImpl( Iterable<T> envelope ) {
        super( envelope );
    }

    public PersistentListImpl( Iterator<T> envelope ) {
        super( envelope );
    }

    @Override protected PersistentListImpl<T> constructor( Envelope<T> envelope ) {
        return new PersistentListImpl<>( envelope );
    }
}
