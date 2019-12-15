package rip.deadcode.abukuma3.filter.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;


public final class LoggingFilter implements AbuFilter {

    public static final LoggingFilter singleton = new LoggingFilter( LoggerFactory.getLogger( LoggingFilter.class ) );

    private final Logger logger;

    public LoggingFilter( Logger logger ) {
        this.logger = logger;
    }

    @Override public AbuResponse filter( AbuExecutionContext context, AbuRequest request, AbuHandler handler ) {
        logger.info( "Request: {} {}", request.method(), request.urlString() );  // TODO
        return handler.handle( context, request );
    }
}
