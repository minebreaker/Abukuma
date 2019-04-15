package rip.deadcode.abukuma3.filter.internal;

import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;


public final class AntiCsrfHeaderFilter implements AbuFilter {

    public static final AbuFilter singleton = new AntiCsrfHeaderFilter();

    private static final String defaultHeader = "X-Requested-With";
    private static final AbuResponse errorResponse = AbuResponses.create( "" ).status( 400 );

    private final String headerName;

    public AntiCsrfHeaderFilter() {
        this( defaultHeader );
    }

    public AntiCsrfHeaderFilter( String headerName ) {
        this.headerName = headerName;
    }

    @Override public AbuResponse filter( AbuRequest request, AbuHandler handler ) {

        if ( request.method().equals( "GET" ) || request.method().equals( "HEADER" ) || request.method().equals( "OPTIONS" )
             || request.header().mayGet( headerName ).isPresent() ) {

            return handler.handle( request );
        }

        return errorResponse;
    }
}
