package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;


class LensTest {

    @Test
    void test() {

        Lens<Map<String, Map<String, String>>, String> lens =
                new MapLens<String, Map<String, String>>( "foo" ).compose( new MapLens<>( "bar" ) );

        Map<String, Map<String, String>> param = ImmutableMap.of( "foo", ImmutableMap.of( "bar", "buz" ) );
        assertThat( lens.get( param ) ).isEqualTo( "buz" );
        assertThat( lens.set( param, "replaced" ) ).containsExactly( "foo", ImmutableMap.of( "bar", "replaced" ) );
    }
}
