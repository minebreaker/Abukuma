package rip.deadcode.abukuma3.handler.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;


public final class DefaultExceptionHandler implements ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger( DefaultExceptionHandler.class );

    @Override public Response handleException( Exception e, ExecutionContext context, Request request ) {
        logger.warn( "An error thrown by the handler.", e );
        return Responses.create( "<h1>500 Internal Server Error</h1>" )
                        .status( 500 )
                        .header( h -> h.contentType( "text/html; charset=utf-8" ) );
    }
}
