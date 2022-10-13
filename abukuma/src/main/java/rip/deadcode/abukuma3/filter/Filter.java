package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;

import javax.annotation.Nullable;


public interface Filter {

    public interface BeforeFilterResult {
    }

    public interface SuccessfulBeforeFilterResult extends BeforeFilterResult {
        public Request<?> request();
    }

    public interface InterruptedBeforeFilterResult extends BeforeFilterResult {
        public Response response();
    }

    public interface AfterFilterResult {
        public Response response();
    }

    /**
     * The function invoked before the handler is called.
     *
     * <p>If the return value is {@code null}, it indicates that
     * the response is "unchanged",
     * which is effectively same as calling
     * {@code Filters.createSuccessfulBeforeFilterResult(request)}.
     */
    @Nullable
    public BeforeFilterResult filterBefore(
            ExecutionContext context,
            Request<?> request );

    /**
     * The function invoked after tha handler is called.
     *
     * <p>If the return value is {@code null}, it indicates that
     * the response is "unchanged",
     * which is effectively same as calling
     * {@code Filters.createAfterFilterResult(response)}.
     */
    @Nullable
    public AfterFilterResult filterAfter(
            ExecutionContext context,
            Request<?> request,
            Response response
    );

    public default Filter then( Filter downstream ) {
        var self = this;
        return new Filter() {
            @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {

                var result = self.filterBefore( context, request );
                if ( result == null ) {
                    return downstream.filterBefore( context, request );
                } else if ( result instanceof SuccessfulBeforeFilterResult ) {
                    var r = ( (SuccessfulBeforeFilterResult) result );
                    return downstream.filterBefore( context, r.request() );
                } else if ( result instanceof InterruptedBeforeFilterResult ) {
                    return result;
                } else {
                    throw new RuntimeException( String.format(
                            "The class is neither SuccessfulBeforeFilterResult nor InterruptedBeforeFilterResult: %s",
                            result.getClass().getCanonicalName()
                    ) );
                }
            }

            @Override
            public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
                var downstreamResult = downstream.filterAfter( context, request, response );
                var downstreamResponse = downstreamResult == null ? response : downstreamResult.response();
                return self.filterAfter( context, request, downstreamResponse );
            }
        };
    }
}
