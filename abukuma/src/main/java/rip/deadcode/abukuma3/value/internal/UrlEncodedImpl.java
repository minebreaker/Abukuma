package rip.deadcode.abukuma3.value.internal;


import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.AbstractPersistentMultimap;
import rip.deadcode.abukuma3.parser.UrlEncoded;


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

    public static UrlEncodedImpl create() {
        return new UrlEncodedImpl();
    }

    public static UrlEncodedImpl create( Multimap<String, String> delegate ) {
        return new UrlEncodedImpl( delegate );
    }

    @Override protected final UrlEncoded constructor( Envelope<String, String> delegate ) {
        return new UrlEncodedImpl( delegate );
    }
}
