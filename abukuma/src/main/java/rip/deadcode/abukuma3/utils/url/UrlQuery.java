package rip.deadcode.abukuma3.utils.url;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UrlQuery extends List<Tuple2<String, String>> {

    public String get( String key );

    public Optional<String> mayGet( String key );

    public PersistentList<String> getAll( String key );

    public UrlQuery add( String key, String value );

    public UrlQuery remove( String key );

    public static UrlQuery fromMap( Map<String, String> params ) {
        throw new UnsupportedOperationException();
    }

    public static UrlQuery fromMultimap( Multimap<String, String> params ) {
        throw new UnsupportedOperationException();
    }
}
