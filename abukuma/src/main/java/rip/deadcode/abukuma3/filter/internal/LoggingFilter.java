package rip.deadcode.abukuma3.filter.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;

import static rip.deadcode.abukuma3.filter.Filters.createSuccessfulBeforeFilterResult;


public final class LoggingFilter implements Filter {

    public static final LoggingFilter singleton = new LoggingFilter( LoggerFactory.getLogger( LoggingFilter.class ) );

    private final Logger logger;

    public LoggingFilter( Logger logger ) {
        this.logger = logger;
    }

    @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {
        logger.info( "Request: {} {}", request.method(), request.urlString() );  // TODO
        return createSuccessfulBeforeFilterResult( request );
    }

    @Override public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
        logger.info( "Response: {}", response.status() ); // TODO
        return null;
    }
}
