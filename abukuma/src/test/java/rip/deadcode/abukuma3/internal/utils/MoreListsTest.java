package rip.deadcode.abukuma3.internal.utils;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static rip.deadcode.abukuma3.internal.utils.MoreLists.first;
import static rip.deadcode.abukuma3.internal.utils.MoreLists.last;

class MoreListsTest {

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
}