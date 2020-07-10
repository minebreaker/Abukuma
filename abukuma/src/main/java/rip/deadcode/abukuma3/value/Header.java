package rip.deadcode.abukuma3.value;

import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.collection.AbstractPersistentListMultimap;


public final class Header extends AbstractPersistentListMultimap<String, String, Header> {

    private Header() {
        super();
    }

    private Header( Envelope<String, String> delegate ) {
        super( delegate );
    }

    public static Header create() {
        return new Header();
    }

    @Override public Header constructor( Envelope<String, String> delegate ) {
        return new Header( delegate );
    }

    public String contentType() {
        return getValue( HttpHeaders.CONTENT_TYPE );
    }

    public Header contentType( String value ) {
        return set( HttpHeaders.CONTENT_TYPE, value );
    }
}
