package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.collection.PersistentList;

import java.util.Optional;
import java.util.function.Function;


public interface Response {

    /**
     * This method can be called multiple times. Must be idempotent i.e. must return same object every time.
     */
    public Object body();

    /**
     * @see #body()
     */
    public Response body( Object body );

    public int status();

    public Response status( int status );

    public Header header();

    public Response header( Header header );

    public PersistentList<Cookie> cookie();

    public Response cookie( PersistentList<Cookie> cookie );

    public Response header( Function<Header, Header> header );

    public Optional<Cookie> cookie( String key );

    public Response addCookie( Cookie cookie );

    public Response addCookie( Cookie first, Cookie... rest );
}
