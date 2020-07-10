package rip.deadcode.abukuma3.handler;


public interface TypeBasedDispatcher extends Handler {

    public TypeBasedDispatcher json( Handler handler );

    public TypeBasedDispatcher xml( Handler handler );

    public TypeBasedDispatcher text( Handler handler );

    public TypeBasedDispatcher fallback( Handler handler );
}
