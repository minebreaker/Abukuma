package rip.deadcode.abukuma3.internal.utils;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.*;


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
}