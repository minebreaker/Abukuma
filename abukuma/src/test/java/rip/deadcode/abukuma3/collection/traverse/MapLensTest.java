package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;


class MapLensTest {

    @Test
    void test() {

        Map<String, String> params = ImmutableMap.of( "k1", "v1", "k2", "v2" );
        Lens<Map<String, String>, String> lens = new MapLens<>( "k2" );

        String retrieved = lens.get( params );
        assertThat( retrieved ).isEqualTo( "v2" );

        Map<String, String> set = lens.set( params, "replaced" );
        assertThat( set ).containsExactly( "k1", "v1", "k2", "replaced" );
    }
}