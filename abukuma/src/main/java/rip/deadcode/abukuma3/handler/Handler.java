package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;


/**
 * Handles a request and returns a response.
 * Implementations must be non-blocking if underlying server implementation
 * you chose is non-blocking.
 */
@FunctionalInterface
public interface Handler {
    public Response handle( ExecutionContext context, Request request );
}
