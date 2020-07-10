package rip.deadcode.abukuma3.filter.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;


public final class LoggingFilter implements Filter {

    public static final LoggingFilter singleton = new LoggingFilter( LoggerFactory.getLogger( LoggingFilter.class ) );

    private final Logger logger;

    public LoggingFilter( Logger logger ) {
        this.logger = logger;
    }

    @Override public Response filter( ExecutionContext context, Request request, Handler handler ) {
        logger.info( "Request: {} {}", request.method(), request.urlString() );  // TODO
        return handler.handle( context, request );
    }
}
