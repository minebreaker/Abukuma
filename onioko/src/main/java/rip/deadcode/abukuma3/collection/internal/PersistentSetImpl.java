package rip.deadcode.abukuma3.collection.internal;

import rip.deadcode.abukuma3.collection.AbstractPersistentSet;

import java.util.Set;


public final class PersistentSetImpl<T>
        extends AbstractPersistentSet<T, PersistentSetImpl<T>> {

    private PersistentSetImpl() {
        super();
    }

    private PersistentSetImpl( Envelope<T> envelope ) {
        super( envelope );
    }

    private PersistentSetImpl( Set<T> envelope ) {
        super( envelope );
    }

    @Override protected PersistentSetImpl<T> constructor( Envelope<T> envelope ) {
        return new PersistentSetImpl<>( envelope );
    }

    public static <T> PersistentSetImpl<T> create() {
        return new PersistentSetImpl<>();
    }

    public static <T> PersistentSetImpl<T> wrap( Set<T> envelope ) {
        return new PersistentSetImpl<>( envelope );
    }
}
