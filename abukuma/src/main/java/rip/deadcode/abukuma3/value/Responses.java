package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.collection.PersistentListImpl;
import rip.deadcode.abukuma3.value.internal.ResponseImpl;


public final class Responses {

    private Responses() {
        throw new Error();
    }

    public static Response create( Object body ) {
        return new ResponseImpl( body, 200, Header.create(), PersistentListImpl.create() );
    }
}
