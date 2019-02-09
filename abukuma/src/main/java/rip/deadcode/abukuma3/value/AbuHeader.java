package rip.deadcode.abukuma3.value;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.internal.AbuAbstractMultimap;

public final class AbuHeader extends AbuAbstractMultimap<AbuHeader> {

    private final Multimap<String, String> delegate;

    private AbuHeader( Multimap<String, String> delegate ) {
        this.delegate = delegate;
    }

    public static AbuHeader create() {
        return new AbuHeader( ImmutableMultimap.of() );
    }

    @Override public AbuHeader constructor( Multimap<String, String> delegate ) {
        return new AbuHeader( delegate );
    }

    @Override protected Multimap<String, String> delegate() {
        return delegate;
    }

    public String contentType() {
        return getValue( HttpHeaders.CONTENT_TYPE );
    }

    public AbuHeader contentType( String value ) {
        return set( HttpHeaders.CONTENT_TYPE, value );
    }
}
