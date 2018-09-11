package rip.deadcode.abukuma3.value;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.internal.AbuMultimap;

public final class AbuHeader extends AbuMultimap {

    private final Multimap<String, String> delegate;

    private AbuHeader( Multimap<String, String> delegate ) {
        this.delegate = delegate;
    }

    public static AbuHeader create() {
        return new AbuHeader( ImmutableMultimap.of() );
    }

    @Override protected Multimap<String, String> delegate() {
        return delegate;
    }

    @Override public AbuHeader copy() {
        return new AbuHeader( ImmutableMultimap.copyOf( delegate ) );
    }

    @Override public AbuHeader set( String key, String value ) {
        return delete( key ).add( key, value );
    }

    @Override public AbuHeader set( String key, Iterable<String> values ) {
        Multimap<String, String> temp = MultimapBuilder.hashKeys().arrayListValues().build();
        temp.putAll( delegate );
        temp.removeAll( key );
        temp.putAll( key, values );
        return new AbuHeader( ImmutableMultimap.copyOf( temp ) );
    }

    @Override public AbuHeader add( String key, String value ) {
        return new AbuHeader( ImmutableMultimap.<String, String>builder().putAll( delegate ).put( key, value ).build() );
    }

    @Override public AbuHeader delete( String key ) {
        Multimap<String, String> temp = MultimapBuilder.hashKeys().arrayListValues().build();
        temp.putAll( delegate );
        temp.removeAll( key );
        return new AbuHeader( ImmutableMultimap.copyOf( temp ) );
    }

    public String contentType() {
        return getValue( HttpHeaders.CONTENT_TYPE );
    }

    public AbuHeader contentType( String value ) {
        return set( HttpHeaders.CONTENT_TYPE, value );
    }
}
