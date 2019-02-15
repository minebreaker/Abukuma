package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nonnull;


@FunctionalInterface
public interface AbuFilter {

    public AbuResponse filter( AbuRequest request, AbuHandler handler ) ;

    @Nonnull
    public default AbuFilter then(AbuFilter downstream) {
        return (request, handler) ->
                filter( request, nextRequest -> downstream.filter( nextRequest, handler ) );
    }
}
