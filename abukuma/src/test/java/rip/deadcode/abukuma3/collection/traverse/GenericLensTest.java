package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;


class GenericLensTest {
    @Test
    void test() {

        Map<String, String> map = ImmutableMap.of( "k", "v" );
        Lens<Object, String> lens = new GenericLens<>( "k" );

        assertThat( lens.get( map ) ).isEqualTo( "v" );

        List<String> list = ImmutableList.of( "foo", "bar" );
        lens = new GenericLens<>( "1" );

        assertThat( lens.get( list ) ).isEqualTo( "bar" );
    }
}
