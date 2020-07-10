package rip.deadcode.abukuma3.collection;


// TODO better name as a public interface?
public final class PersistentListImpl<T> extends AbstractPersistentList<T> {

    private PersistentListImpl() {
        super();
    }

    private PersistentListImpl( Envelope<T> envelope ) {
        super( envelope );
    }

    public static <T> PersistentListImpl<T> create() {
        return new PersistentListImpl<>();
    }

    // TODO
//    public static <T> AbuPersistentList<T> create(T first, T... rest) {
//        return new AbuPersistentList<>();
//    }

    @Override protected PersistentList<T> constructor( Envelope<T> envelope ) {
        return new PersistentListImpl<>( envelope );
    }
}
