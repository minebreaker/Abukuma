package rip.deadcode.abukuma3.utils.url.internal;


import rip.deadcode.abukuma3.collection.AbstractPersistentList;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.Tuple2;
import rip.deadcode.abukuma3.utils.url.UrlQuery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static rip.deadcode.abukuma3.collection.PersistentCollections.tuple;
import static rip.deadcode.abukuma3.collection.PersistentCollectors.toPersistentList;


public final class UrlQueryImpl
        extends AbstractPersistentList<Tuple2<String, String>, UrlQueryImpl>
        implements UrlQuery {

    public UrlQueryImpl() {
        super();
    }

    private UrlQueryImpl( List<Tuple2<String, String>> delegate ) {
        super( delegate );
    }

    private UrlQueryImpl( Envelope<Tuple2<String, String>> delegate ) {
        super( delegate );
    }

    @Override protected UrlQueryImpl constructor( Envelope<Tuple2<String, String>> envelope ) {
        return new UrlQueryImpl( envelope );
    }

    @Override public String get( String key ) {
        return mayGet( key ).orElse( null );
    }

    @Override public Optional<String> mayGet( String key ) {
        return this.stream()
                   .filter( t -> Objects.equals( t.getKey(), key ) )
                   .findFirst()
                   .map( Tuple2::getValue );
    }

    @Override public PersistentList<String> getAll( String key ) {
        return this.stream()
                   .filter( t -> Objects.equals( t.getKey(), key ) )
                   .map( Tuple2::getValue )
                   .collect( toPersistentList() );
    }

    @Override public UrlQueryImpl add( String key, String value ) {
        return addLast( tuple( key, value ) );
    }

    @Override public UrlQueryImpl remove( String key ) {
        var l = this.stream()
                    .filter( t -> !Objects.equals( t.getKey(), key ) )
                    .collect( toPersistentList() );
        return new UrlQueryImpl( l );
    }
}
