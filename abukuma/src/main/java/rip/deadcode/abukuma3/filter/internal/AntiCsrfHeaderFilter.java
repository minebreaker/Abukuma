package rip.deadcode.abukuma3.filter.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;

import static rip.deadcode.abukuma3.filter.Filters.createInterruptedBeforeFilterResult;
import static rip.deadcode.abukuma3.filter.Filters.createSuccessfulBeforeFilterResult;


public final class AntiCsrfHeaderFilter implements Filter {

    private static final String defaultHeader = "X-Requested-With";

    public static final Filter singleton = new AntiCsrfHeaderFilter( defaultHeader );

    private static final BeforeFilterResult errorResponse = createInterruptedBeforeFilterResult(
            Responses.create( "" ).status( 400 ) );

    private final String headerName;

    public AntiCsrfHeaderFilter( String headerName ) {
        this.headerName = headerName;
    }

    @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {
        if ( request.method().equals( "GET" )
             || request.method().equals( "HEADER" )
             || request.method().equals( "OPTIONS" )
             || request.header().mayGet( headerName ).isPresent() ) {

            return createSuccessfulBeforeFilterResult( request );
        }

        return errorResponse;
    }

    @Override public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
        return null;
    }
}
