package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.collection.PersistentCollectors.toPersistentList;
import static rip.deadcode.abukuma3.collection.PersistentCollectors.toPersistentMap;
import static rip.deadcode.abukuma3.collection.PersistentCollectors.toPersistentMultimap;


class PersistentCollectorsTest {

    @Test
    public void testToList() {
        var list = IntStream
                .range( 1, 101 )
                .boxed()
                .collect( toPersistentList() );

        for ( int i = 1; i <= 100; i++ ) {
            assertThat( list.get( i - 1 ) ).isEqualTo( i );
        }
    }

    @Test
    public void testToMap() {
        var map = IntStream
                .range( 1, 101 )
                .boxed()
                .collect( toPersistentMap( i -> "key" + i, i -> "value" + i ) );

        for ( int i = 1; i <= 100; i++ ) {
            assertThat( map.get( "key" + i ) ).isEqualTo( "value" + i );
        }
    }

    @Test
    public void testToMultimap() {
        var multimap = IntStream
                .range( 1, 21 )
                .boxed()
                .collect( toPersistentMultimap( i -> "key" + i % 5, i -> "value" + i ) );

        for ( int i = 1; i < 5; i++ ) {
            assertThat( multimap.get( "key" + i ) )
                    .containsExactly(
                            "value" + i,
                            "value" + ( i + 5 ),
                            "value" + ( i + 5 * 2 ),
                            "value" + ( i + 5 * 3 )
                    )
                    .inOrder();
            assertThat( multimap.size() ).isEqualTo( 20 );
        }
    }
}
