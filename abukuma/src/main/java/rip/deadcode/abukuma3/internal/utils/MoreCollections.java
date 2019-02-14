package rip.deadcode.abukuma3.internal.utils;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkState;


public final class MoreCollections {

    private MoreCollections() {}

    public static <T> T first( Iterable<T> iter ) {
        Iterator<T> i = iter.iterator();
        checkState( i.hasNext() );

        return i.next();
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
}
