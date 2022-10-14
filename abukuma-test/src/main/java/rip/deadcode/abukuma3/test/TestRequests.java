package rip.deadcode.abukuma3.test;

import rip.deadcode.abukuma3.value.Request;


public final class TestRequests {

    public static Request<Request.Empty> create() {
        throw new UnsupportedOperationException();
    }

    public static <T> Request<T> create( T body ) {
        throw new UnsupportedOperationException();
    }
}
