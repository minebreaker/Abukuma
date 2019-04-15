package rip.deadcode.abukuma3.value;

import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.value.internal.AbuResponseImpl;


public final class AbuResponses {

    public static AbuResponse create( Object body ) {
        return new AbuResponseImpl( body, 200, AbuHeader.create(), ImmutableList.of() );
    }
}
