package rip.deadcode.abukuma3.handler;


public interface TypeBasedDispatcher extends AbuHandler {

    public TypeBasedDispatcher json( AbuHandler handler );

    public TypeBasedDispatcher xml( AbuHandler handler );

    public TypeBasedDispatcher text( AbuHandler handler );

    public TypeBasedDispatcher fallback( AbuHandler handler );
}
