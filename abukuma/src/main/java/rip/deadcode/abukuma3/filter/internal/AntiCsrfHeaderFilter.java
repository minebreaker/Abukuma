package rip.deadcode.abukuma3.filter.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;


public final class AntiCsrfHeaderFilter implements Filter {

    public static final Filter singleton = new AntiCsrfHeaderFilter();

    private static final String defaultHeader = "X-Requested-With";
    private static final Response errorResponse = Responses.create( "" ).status( 400 );

    private final String headerName;

    public AntiCsrfHeaderFilter() {
        this( defaultHeader );
    }

    public AntiCsrfHeaderFilter( String headerName ) {
        this.headerName = headerName;
    }

    @Override public Response filter( ExecutionContext context, Request request, Handler handler ) {

        if ( request.method().equals( "GET" )
             || request.method().equals( "HEADER" )
             || request.method().equals( "OPTIONS" )
             || request.header().mayGet( headerName ).isPresent() ) {

            return handler.handle( context, request );
        }

        return errorResponse;
    }
}
