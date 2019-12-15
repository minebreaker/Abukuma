package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nonnull;


/**
 * If filter consumed the request body, filter MUST NOT invoke given handler.
 */
@FunctionalInterface
public interface AbuFilter {

    public AbuResponse filter( AbuExecutionContext context, AbuRequest request, AbuHandler handler );

    @Nonnull
    public default AbuFilter then( AbuFilter downstream ) {
        return ( context, request, handler ) ->
                filter(
                        context,
                        request,
                        ( nextContext, nextRequest ) -> downstream.filter( nextContext, nextRequest, handler )
                );
    }
}
