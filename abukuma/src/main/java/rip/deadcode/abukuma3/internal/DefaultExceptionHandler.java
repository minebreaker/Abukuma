package rip.deadcode.abukuma3.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;

public final class DefaultExceptionHandler implements AbuExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger( DefaultExceptionHandler.class );

    @Override public AbuResponse handleException( Exception e, AbuRequest request ) {
        logger.warn( "An error thrown by the handler.", e );
        return AbuResponse.create( "<h1>500 Internal Server Error</h1>" )
                          .header( h -> h.contentType( "text/html; charset=utf-8" ) );
    }
}
