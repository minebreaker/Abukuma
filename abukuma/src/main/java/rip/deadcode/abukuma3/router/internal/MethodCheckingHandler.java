package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;


public final class MethodCheckingHandler implements AbuHandler {

    private static final AbuResponse methodNotAllowed = AbuResponse.create( "<h1>405 Method Not Allowed</h1>" )
                                                                   .status( 405 );

    private final String method;
    private final AbuHandler handler;

    public MethodCheckingHandler( String method, AbuHandler handler ) {
        this.method = method;
        this.handler = handler;
    }

    @Override public AbuResponse handle( AbuRequest request ) {

        // TODO should have option that returns 404
        if ( !request.method().equals( method ) ) {
            return methodNotAllowed;
        }

        return handler.handle( request );
    }
}
