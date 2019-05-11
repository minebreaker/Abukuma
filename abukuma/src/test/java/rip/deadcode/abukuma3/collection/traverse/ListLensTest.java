package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;


class ListLensTest {

    @Test
    void test() {

        List<String> params = ImmutableList.of( "foo", "bar", "buz" );
        Lens<List<String>, String> lens = new ListLens<>( 1 );

        String retrieved = lens.get( params );
        assertThat( retrieved ).isEqualTo( "bar" );

        List<String> set = lens.set( params, "replaced" );
        assertThat( set ).containsExactly( "foo", "replaced", "buz" ).inOrder();
    }
}