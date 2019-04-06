package rip.deadcode.abukuma3.internal.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkState;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.also;


public final class MoreCollections {

    private MoreCollections() {}

    public static <T> T first( Iterable<T> iter ) {
        Iterator<T> i = iter.iterator();
        checkState( i.hasNext() );

        return i.next();
    }

    public static <T> Optional<T> mayFirst( Iterable<T> iter ) {
        Iterator<T> i = iter.iterator();
        return i.hasNext() ? Optional.of( i.next() ) : Optional.empty();
    }

    public static <T> T last( Iterable<T> iter ) {
        Iterator<T> i = iter.iterator();
        checkState( i.hasNext() );

        while ( true ) {
            T v = i.next();
            if ( !i.hasNext() ) {
                return v;
            }
        }
    }

    public static <T, U, V> Iterable<V> zip( Iterable<T> iter1, Iterable<U> iter2, BiFunction<T, U, V> zipper ) {
        Iterator<T> i1 = iter1.iterator();
        Iterator<U> i2 = iter2.iterator();

        return new Iterable<V>() {
            @Nonnull @Override public Iterator<V> iterator() {
                return new Iterator<V>() {
                    @Override public boolean hasNext() {
                        return i1.hasNext() && i2.hasNext();
                    }

                    @Override public V next() {
                        return zipper.apply( i1.next(), i2.next() );
                    }
                };
            }
        };
    }

    public static <T, U, V> List<V> zipToList( Iterable<T> iter1, Iterable<U> iter2, BiFunction<T, U, V> zipper ) {
        return also(
                new ArrayList<>(),
                e -> Iterables.addAll( e, zip( iter1, iter2, zipper ) )
        );
    }

    // TODO should use persistent map
    public static <K, V> Map<K, V> assoc( Map<K, V> into, K key, V value ) {
        if ( into.containsKey( key ) ) {
            return ImmutableMap.copyOf(
                    also( new HashMap<>(), m -> {
                        m.putAll( into );
                        m.put( key, value );
                    } )
            );

        } else {
            return ImmutableMap.<K, V>builder()
                    .putAll( into )
                    .put( key, value )
                    .build();
        }
    }
}
