package rip.deadcode.abukuma3.internal.utils;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.first;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.last;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.reduce;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.zip;


class MoreCollectionsTest {

    @Test
    void testFirst() {
        String result = first( ImmutableList.of( "foo", "bar", "buz" ) );
        assertThat( result ).isEqualTo( "foo" );
    }

    @Test
    void testLast() {
        String result = last( ImmutableList.of( "foo", "bar", "buz" ) );
        assertThat( result ).isEqualTo( "buz" );
    }

    @Test
    void testZip() {
        Iterable<String> result = zip( ImmutableList.of( "a", "b", "c" ), ImmutableList.of( "1", "2", "3" ), ( p, q ) -> p + q );
        assertThat( result ).containsExactly( "a1", "b2", "c3" );
    }

    @Test
    void testReduce() {
        List<String> params = ImmutableList.of( "foo", "bar", "buzz" );
        int result = reduce( params, 0, ( acc, s ) -> acc + s.length() );

        assertThat( result ).isEqualTo( 10 );

        assertThat( reduce( ImmutableList.<String>of(), 0, ( acc, s ) -> acc + s.length() ) ).isEqualTo( 0 );
    }
}