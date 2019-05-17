package rip.deadcode.abukuma3.collection;


public final class AbuPersistentList<T> extends AbstractPersistentList<T> {

    private AbuPersistentList() {
        super();
    }

    private AbuPersistentList( Envelope<T> envelope ) {
        super( envelope );
    }

    @Override protected PersistentList<T> constructor( Envelope<T> envelope ) {
        return new AbuPersistentList<>( envelope );
    }
}
