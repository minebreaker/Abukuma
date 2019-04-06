package rip.deadcode.abukuma3.value;

import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.collection.AbstractPersistentListMultimap;


public final class AbuHeader extends AbstractPersistentListMultimap<String, String, AbuHeader> {

    private AbuHeader() {
        super();
    }

    private AbuHeader( Envelope<String, String> delegate ) {
        super( delegate );
    }

    public static AbuHeader create() {
        return new AbuHeader();
    }

    @Override public AbuHeader constructor( Envelope<String, String> delegate ) {
        return new AbuHeader( delegate );
    }

    public String contentType() {
        return getValue( HttpHeaders.CONTENT_TYPE );
    }

    public AbuHeader contentType( String value ) {
        return set( HttpHeaders.CONTENT_TYPE, value );
    }
}
