package rip.deadcode.abukuma3.utils.url;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.Tuple2;

import java.util.Map;
import java.util.Optional;


public interface UrlQuery extends PersistentList<Tuple2<String, String>> {

    public String get( String key );

    public Optional<String> mayGet( String key );

    public PersistentList<String> getAll( String key );

    public UrlQuery add( String key, String value );

    public UrlQuery remove( String key );

    @Override public PersistentList<Tuple2<String, String>> addFirst( Tuple2<String, String> value );

    @Override public PersistentList<Tuple2<String, String>> addLast( Tuple2<String, String> value );

    @Override public PersistentList<Tuple2<String, String>> insert( int n, Tuple2<String, String> value );

    @Override public PersistentList<Tuple2<String, String>> replace( int n, Tuple2<String, String> value );

    @Override public PersistentList<Tuple2<String, String>> delete( int n );

    @Override public UrlQuery concat( Iterable<? extends Tuple2<String, String>> list );

    public static UrlQuery fromMap( Map<String, String> params ) {
        throw new UnsupportedOperationException();
    }

    public static UrlQuery fromMultimap( Multimap<String, String> params ) {
        throw new UnsupportedOperationException();
    }
}
