package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;


public final class Header extends AbstractPersistentMultimap<String, String> {

    private Header() {
        super();
    }

    private Header( Multimap<String, String> delegate ) {
        super( delegate );
    }

    private Header( Envelope<String, String> delegate ) {
        super( delegate );
    }

    public static Header create() {
        return new Header();
    }

    public static Header create( Multimap<String, String> delegate ) {
        return new Header( delegate );
    }

    @Override protected Header constructor( Envelope<String, String> delegate ) {
        return new Header( delegate );
    }

    public String contentType() {
        return getValue( HttpHeaders.CONTENT_TYPE );
    }

    public Header contentType( String value ) {
        return new Header( set( HttpHeaders.CONTENT_TYPE, value ) );
    }
}
