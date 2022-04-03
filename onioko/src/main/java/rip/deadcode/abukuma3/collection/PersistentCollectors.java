package rip.deadcode.abukuma3.collection;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;

import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableMap;


public final class PersistentCollectors {

    private PersistentCollectors() {
        throw new Error();
    }

    //  If you want Persistent reducing
//    private static final Collector<Object, ?, PersistentList<Object>>
//            listCollector = Collectors.reducing(
//            PersistentCollections.createList(),
//            e -> PersistentCollections.createList(e),
//            PersistentList::concat
//    );

    // If you want to directly reduce stream
//    PersistentList<String> s = Stream
//            .of( "1", "2", "3" )
//            .reduce(
//                    PersistentCollections.createList(),
//                    PersistentList::addLast,
//                    PersistentList::concat
//            );

    private static final Collector<Object, ?, PersistentList<Object>> listCollector =
            collectingAndThen(
                    toUnmodifiableList(),
                    PersistentCollections::wrapList
            );

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public static <T> Collector<T, ?, PersistentList<T>> toPersistentList() {
        return (Collector) listCollector;
    }

    @SuppressWarnings( "unchecked" )
    public static <T, K, V> Collector<T, ?, PersistentMap<K, V>> toPersistentMap(
            Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends V> valueMapper ) {

        return collectingAndThen(
                toUnmodifiableMap( keyMapper, valueMapper ),
                map -> (PersistentMap<K, V>) PersistentCollections.wrapMap( map )
        );
    }

    @SuppressWarnings( "unchecked" )
    public static <T, K, V> Collector<T, ?, PersistentMultimap<K, V>> toPersistentMultimap(
            Function<? super T, ? extends K> keyFunction,
            Function<? super T, ? extends V> valueFunction
    ) {
        return collectingAndThen(
                Multimaps.toMultimap(
                        keyFunction,
                        valueFunction,
                        MultimapBuilder.hashKeys().arrayListValues()::build
                ),
                map -> (PersistentMultimap<K, V>) PersistentCollections.wrapMultimap( map )
        );
    }
}
