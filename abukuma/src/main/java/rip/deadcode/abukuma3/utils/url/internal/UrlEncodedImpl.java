package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;
import rip.deadcode.abukuma3.value.UrlEncoded;


public final class UrlEncodedImpl
        extends AbstractPersistentMultimap<String, String, UrlEncoded>
        implements UrlEncoded {

    private UrlEncodedImpl() {
        super();
    }

    private UrlEncodedImpl( Multimap<String, String> delegate ) {
        super( delegate );
    }

    private UrlEncodedImpl( Envelope<String, String> delegate ) {
        super( delegate );
    }

    @Override protected final UrlEncodedImpl constructor( Envelope<String, String> delegate ) {
        return new UrlEncodedImpl( delegate );
    }

    public static UrlEncoded create() {
        return new UrlEncodedImpl();
    }

    public static UrlEncoded cast( Multimap<String, String> delegate ) {
        return new UrlEncodedImpl( delegate );
    }
}
