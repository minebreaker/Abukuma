package rip.deadcode.abukuma3.utils.url.internal;


import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.Tuple2;
import rip.deadcode.abukuma3.utils.url.UrlQuery;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.tuple;


public final class UrlQueryImpl implements UrlQuery {

    private final PersistentList<Tuple2<String, String>> internal;

    public UrlQueryImpl() {
        this.internal = createList();
    }

    private UrlQueryImpl( PersistentList<Tuple2<String, String>> internal ) {
        this.internal = internal;
    }

    @Override public String get( String key ) {
        // TODO: impl should be O(n) ... but how?
        //noinspection OptionalGetWithoutIsPresent
        return mayGet( key ).get();
    }

    @Override public Optional<String> mayGet( String key ) {
        return internal.stream()
                       .filter( e -> Objects.equals( e.getKey(), key ) )
                       .findFirst()
                       .map( Map.Entry::getValue );
    }

    @Override public PersistentList<String> getAll( String key ) {
        // TODO persistent collector
        return PersistentCollections.wrapList(
                internal.stream()
                        .filter( e -> Objects.equals( e.getKey(), key ) )
                        .map( Map.Entry::getValue )
                        .collect( toList() ) );
    }

    @Override public UrlQuery add( String key, String value ) {
        return new UrlQueryImpl( internal.addLast( tuple( key, value ) ) );
    }

    @Override public UrlQuery remove( String key ) {
        // TODO persistent collector
        return new UrlQueryImpl( PersistentCollections.wrapList(
                internal.stream()
                        .filter( e -> !Objects.equals( e.getKey(), key ) )
                        .collect( toList() ) ) );
    }

    @Override public int size() {
        return internal.size();
    }

    @Override public boolean isEmpty() {
        return internal.isEmpty();
    }

    @Override public boolean contains( Object o ) {
        return internal.stream().anyMatch( e -> Objects.equals( e.getValue(), o ) );
    }

    @Override public Iterator<Tuple2<String, String>> iterator() {
        return internal.iterator();
    }

    @Override public Object[] toArray() {
        return internal.toArray();
    }

    @Override public <T> T[] toArray( T[] a ) {
        return internal.toArray( a );
    }

    @Override public Tuple2<String, String> get( int index ) {
        return internal.get( index );
    }

    @Override public int indexOf( Object o ) {
        return internal.indexOf( o );
    }

    @Override public int lastIndexOf( Object o ) {
        return internal.lastIndexOf( o );
    }

    @Override public ListIterator<Tuple2<String, String>> listIterator() {
        return internal.listIterator();
    }

    @Override public ListIterator<Tuple2<String, String>> listIterator( int index ) {
        return internal.listIterator();
    }

    @Override public List<Tuple2<String, String>> subList( int fromIndex, int toIndex ) {
        return internal.subList( fromIndex, toIndex );
    }

    @Override public boolean add( Tuple2<String, String> stringStringTuple2 ) {
        throw new UnsupportedOperationException();
    }

    @Override public Tuple2<String, String> set(
            int index, Tuple2<String, String> element ) {
        throw new UnsupportedOperationException();
    }

    @Override public void add( int index, Tuple2<String, String> element ) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean remove( Object o ) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean containsAll( Collection<?> c ) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean addAll( Collection<? extends Tuple2<String, String>> c ) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean addAll( int index, Collection<? extends Tuple2<String, String>> c ) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean removeAll( Collection<?> c ) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean retainAll( Collection<?> c ) {
        throw new UnsupportedOperationException();
    }

    @Override public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override public Tuple2<String, String> remove( int index ) {
        throw new UnsupportedOperationException();
    }
}
