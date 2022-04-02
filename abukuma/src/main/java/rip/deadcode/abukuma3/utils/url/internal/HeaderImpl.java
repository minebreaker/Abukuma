package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;
import rip.deadcode.abukuma3.value.Header;

import java.util.Optional;


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

    @Override protected final HeaderImpl constructor( Envelope<String, String> delegate ) {
        return new HeaderImpl( delegate );
    }

    @Override public Optional<String> contentType() {
        return Optional.ofNullable( getValue( HttpHeaders.CONTENT_TYPE ) );
    }

    @Override public Header contentType( String value ) {
        return set( HttpHeaders.CONTENT_TYPE, value );
    }

    public static Header create() {
        return new HeaderImpl();
    }

    public static Header cast( Multimap<String, String> delegate ) {
        return new HeaderImpl( delegate );
    }
}
