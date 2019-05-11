package rip.deadcode.abukuma3.collection;


public final class AbuPersistentList<T> extends AbstractPersistentList<T, AbuPersistentList<T>> {

    private AbuPersistentList() {
        super();
    }

    private AbuPersistentList( Envelope<T> delegate ) {
        super( delegate );
    }

    public static <T> AbuPersistentList<T> create() {
        return new AbuPersistentList<>();
    }

    @Override
    protected AbuPersistentList<T> constructor( Envelope<T> delegate ) {
        return new AbuPersistentList<>( delegate );
    }
}
