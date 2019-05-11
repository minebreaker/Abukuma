package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

class OpticsTest {

    @Test
    void experiment() {

        Map<String, List<Map<String, String>>> obj = ImmutableMap.of(
                "foo", ImmutableList.of(
                        ImmutableMap.of( "k1", "v1" ),
                        ImmutableMap.of( "k2", "v2" )
                )
        );
        Lens<Map<String, List<Map<String, String>>>, String> lens = Optics.path( "foo/1/k2" );

        assertThat( lens.get( obj ) ).isEqualTo( "v2" );
    }

    private static final class User {
        private int id;
        private String name;
        @Nullable
        private String email;

        private User( int id, String name, @Nullable String email ) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    @Test
    void test() {
    }
}
