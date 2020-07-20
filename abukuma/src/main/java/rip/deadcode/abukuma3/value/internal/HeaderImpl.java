package rip.deadcode.abukuma3.value.internal;

import rip.deadcode.abukuma3.value.Header;
import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;


public final class HeaderImpl
        extends AbstractPersistentMultimap<String, String, Header>
        implements Header {

    private HeaderImpl() {
        super();
    }

    private HeaderImpl( Multimap<String, String> delegate ) {
        super( delegate );
    }

    private HeaderImpl( Envelope<String, String> delegate ) {
        super( delegate );
    }

    public static HeaderImpl create() {
        return new HeaderImpl();
    }

    public static HeaderImpl create( Multimap<String, String> delegate ) {
        return new HeaderImpl( delegate );
    }

    @Override protected final HeaderImpl constructor( Envelope<String, String> delegate ) {
        return new HeaderImpl( delegate );
    }

    @Override public String contentType() {
        return getValue( HttpHeaders.CONTENT_TYPE );
    }

    @Override public Header contentType( String value ) {
        return set( HttpHeaders.CONTENT_TYPE, value );
    }
}
