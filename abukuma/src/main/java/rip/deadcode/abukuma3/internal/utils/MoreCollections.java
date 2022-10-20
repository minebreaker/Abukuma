package rip.deadcode.abukuma3.internal.utils;

import rip.deadcode.abukuma3.collection.PersistentList;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapList;


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

        return new Iterable<>() {
            @Nonnull @Override public Iterator<V> iterator() {
                return new Iterator<>() {
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

    public static <T, U, V> PersistentList<V> zipToList(
            Iterable<T> iter1,
            Iterable<U> iter2,
            BiFunction<T, U, V> zipper ) {
        return wrapList( zip( iter1, iter2, zipper ) );
    }

    public static <T, R> R reduceSequentially( Iterable<T> iterable, R identity, BiFunction<R, T, R> accumulator ) {

        R r = checkNotNull( identity );

        for ( T t : iterable ) {
            r = accumulator.apply( r, t );
        }

        return r;
    }
}
