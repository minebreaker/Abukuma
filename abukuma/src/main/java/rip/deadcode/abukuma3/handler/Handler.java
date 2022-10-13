package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;


/**
 * Handles a request and returns a response.
 * Implementations must be non-blocking if underlying server implementation
 * you chose is non-blocking.
 */
public interface Handler<T> {

    public Class<T> bodyType();

    public Response handle( ExecutionContext context, Request<? extends T> request );
}
