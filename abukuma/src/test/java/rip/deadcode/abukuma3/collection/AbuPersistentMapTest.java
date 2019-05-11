package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;


class AbuPersistentMapTest {
    @Test
    void test() {

        AbuPersistentMap<String, String> map = AbuPersistentMap.create();
        assertThat( map ).isEmpty();

        map = map.set( "k1", "v1" ).set( "k2", "v2" );
        assertThat( map ).containsExactly( "k1", "v1", "k2", "v2" );
        assertThat( map.delete( "k2" ) ).doesNotContainKey( "k2" );
    }
}
