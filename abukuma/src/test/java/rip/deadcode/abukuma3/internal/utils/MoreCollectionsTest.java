package rip.deadcode.abukuma3.internal.utils;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.collection.PersistentCollections;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.first;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.last;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.reduceSequentially;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.zip;


class MoreCollectionsTest {

    @Test
    void testFirst() {
        String result = first( createList( "foo", "bar", "buz" ) );
        assertThat( result ).isEqualTo( "foo" );
    }

    @Test
    void testLast() {
        String result = last( createList( "foo", "bar", "buz" ) );
        assertThat( result ).isEqualTo( "buz" );
    }

    @Test
    void testZip() {
        Iterable<String> result = zip( createList( "a", "b", "c" ), createList( "1", "2", "3" ), ( p, q ) -> p + q );
        assertThat( result ).containsExactly( "a1", "b2", "c3" );
    }

    @Test
    void testReduce() {
        var params = createList( "foo", "bar", "buzz" );
        int result = reduceSequentially( params, 0, ( acc, s ) -> acc + s.length() );

        assertThat( result ).isEqualTo( 10 );

        assertThat( reduceSequentially( PersistentCollections.<String>createList(), 0, ( acc, s ) -> acc + s.length() ) )
                .isEqualTo( 0 );
    }
}
