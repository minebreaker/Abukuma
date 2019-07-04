package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.collection.AbuPersistentList;
import rip.deadcode.abukuma3.value.internal.AbuResponseImpl;


public final class AbuResponses {

    public static AbuResponse create( Object body ) {
        return new AbuResponseImpl( body, 200, AbuHeader.create(), AbuPersistentList.create() );
    }
}
