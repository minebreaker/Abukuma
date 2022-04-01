package rip.deadcode.abukuma3.utils.url.internal;

import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.Header;
import rip.deadcode.abukuma3.value.Response;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;


public final class ResponseImpl implements Response {

    public ResponseImpl(
            Object body, int status, Header header, PersistentList<Cookie> cookie
    ) {
        this.body = checkNotNull( body );
        this.status = status;
        this.header = checkNotNull( header );
        this.cookie = checkNotNull( cookie );
    }

    private ResponseImpl copy() {
        return new ResponseImpl( body, status, header, cookie );
    }

    private Object body;

    @Override
    public Object body() {
        return body;
    }

    @Override
    public ResponseImpl body( Object body ) {
        checkNotNull( body );
        ResponseImpl copy = copy();
        copy.body = body;
        return copy;
    }

    private int status;

    @Override public int status() {
        return status;
    }

    @Override
    public ResponseImpl status(
            int status
    ) {

        ResponseImpl copy = copy();
        copy.status = status;
        return copy;
    }

    private Header header;

    @Override
    public Header header() {
        return header;
    }

    @Override
    public ResponseImpl header(
            Header header
    ) {
        checkNotNull( header );
        ResponseImpl copy = copy();
        copy.header = header;
        return copy;
    }

    private PersistentList<Cookie> cookie;

    @Override
    public PersistentList<Cookie> cookie() {
        return cookie;
    }

    @Override
    public ResponseImpl cookie(
            PersistentList<Cookie> cookie
    ) {
        checkNotNull( cookie );
        ResponseImpl copy = copy();
        copy.cookie = cookie;
        return copy;
    }

    @Override
    public Response header( Function<Header, Header> header ) {
        ResponseImpl r = this.copy();
        r.header = header.apply( this.header );
        return r;

    }

    @Override
    public Optional<Cookie> cookie( String key ) {
        return cookie.stream()
                     .filter( c -> c.name().equals( key ) )
                     .findAny();

    }

    @Override
    public Response addCookie( Cookie cookie ) {
        ResponseImpl r = copy();
        r.cookie = this.cookie.addLast( cookie );
        return r;

    }

    @Override
    public Response addCookie( Cookie first, Cookie... rest ) {
        ResponseImpl r = copy();
        r.cookie = this.cookie.addLast( first ).concat( Arrays.asList( rest ) );
        return r;

    }
}
