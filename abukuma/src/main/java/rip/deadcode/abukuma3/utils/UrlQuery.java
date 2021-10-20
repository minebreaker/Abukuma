package rip.deadcode.abukuma3.utils;

import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UrlQuery {

    public String get( String key );

    public Optional<String> mayGet( String key );

    public List<String> getAll( String key );

    public UrlQuery add( String key, String value );

    public UrlQuery set( String key, String value );

    public UrlQuery set( String key, List<String> values );

    public UrlQuery remove( String key );

    public String serialize();

    public static UrlQuery fromMap( Map<String, String> params ) {
        throw new UnsupportedOperationException();
    }

    public static UrlQuery fromMultimap( Multimap<String, String> params ) {
        throw new UnsupportedOperationException();
    }

    public static UrlQuery createRaw( String queryString ) {
        throw new UnsupportedOperationException();
    }
}
