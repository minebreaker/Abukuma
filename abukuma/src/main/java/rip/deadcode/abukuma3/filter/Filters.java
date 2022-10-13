package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.internal.AntiCsrfHeaderFilter;
import rip.deadcode.abukuma3.filter.internal.BasicAuthFilter;
import rip.deadcode.abukuma3.filter.internal.LoggingFilter;
import rip.deadcode.abukuma3.filter.internal.NoopFilter;
import rip.deadcode.abukuma3.internal.utils.Function3;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;

import java.util.function.BiFunction;
import java.util.function.Predicate;


public final class Filters {

    private Filters() {
        throw new Error();
    }

    private static final class SuccessfulBeforeFilterResultImpl implements Filter.SuccessfulBeforeFilterResult {

        private final Request<?> request;

        private SuccessfulBeforeFilterResultImpl( Request<?> request ) {
            this.request = request;
        }

        @Override public Request<?> request() {
            return request;
        }
    }

    private static final class InterruptedBeforeFilterResultImpl implements Filter.InterruptedBeforeFilterResult {

        private final Response response;

        private InterruptedBeforeFilterResultImpl( Response response ) {
            this.response = response;
        }

        @Override public Response response() {
            return response;
        }
    }

    private static final class AfterFilterResultImpl implements Filter.AfterFilterResult {

        private final Response response;

        private AfterFilterResultImpl( Response response ) {
            this.response = response;
        }

        @Override public Response response() {
            return response;
        }
    }

    public static Filter.SuccessfulBeforeFilterResult createSuccessfulBeforeFilterResult( Request<?> request ) {
        return new SuccessfulBeforeFilterResultImpl( request );
    }

    public static Filter.InterruptedBeforeFilterResult createInterruptedBeforeFilterResult( Response response ) {
        return new InterruptedBeforeFilterResultImpl( response );
    }

    public static Filter.AfterFilterResult createAfterFilterResult( Response response ) {
        return new AfterFilterResultImpl( response );
    }

    public static Filter createBeforeFilter( BiFunction<ExecutionContext, Request<?>, Filter.BeforeFilterResult> filter ) {
        return new Filter() {
            @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {
                return filter.apply( context, request );
            }

            @Override
            public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
                return null;
            }
        };
    }

    public static Filter createAfterFilter( Function3<ExecutionContext, Request<?>, Response, Filter.AfterFilterResult, RuntimeException> filter ) {
        return new Filter() {
            @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {
                return null;
            }

            @Override
            public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
                return filter.apply( context, request, response );
            }
        };
    }

    public static Filter noop() {
        return NoopFilter.singleton;
    }

    public static Filter logging() {
        return LoggingFilter.singleton;
    }

    public static Filter antiCsrf() {
        return AntiCsrfHeaderFilter.singleton;
    }

    public static Filter basicAuth( Predicate<AuthRequest> accepts ) {
        return new BasicAuthFilter( accepts );
    }
}
