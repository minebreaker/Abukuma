package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nonnull;


/**
 * If filter consumed the request body, filter MUST NOT invoke given handler.
 */
@FunctionalInterface
public interface Filter {

    public Response filter( ExecutionContext context, Request request, Handler handler );

    @Nonnull
    public default Filter then( Filter downstream ) {
        return ( context, request, handler ) ->
                filter(
                        context,
                        request,
                        ( nextContext, nextRequest ) -> downstream.filter( nextContext, nextRequest, handler )
                );
    }
}
