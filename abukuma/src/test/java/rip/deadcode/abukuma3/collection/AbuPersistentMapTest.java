package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;


class AbuPersistentMapTest {

    @Test
    void testMayGet() {
        assertThat( AbuPersistentMap.create() ).isEmpty();

        assertThat( AbuPersistentMap.create().set( "k", "v" ).mayGet( "k" ) ).hasValue( "v" );
        assertThat( AbuPersistentMap.create().set( "k", "v" ).mayGet( "nothing" ) ).isEmpty();
    }

    @Test
    void testSet() {

        AbuPersistentMap<String, String> param = AbuPersistentMap.<String, String>create()
                .set( "k1", "v1" ).set( "k2", "v2" );
        assertThat( param ).containsExactly( "k1", "v1", "k2", "v2" );
        assertThat( param.set( "k2", "replaced" ) ).containsExactly( "k1", "v1", "k2", "replaced" );
    }

    @Test
    void testDelete() {

        AbuPersistentMap<String, String> param = AbuPersistentMap.<String, String>create()
                .set( "k1", "v1" ).set( "k2", "v2" );
        assertThat( param.delete( "k2" ) ).containsExactly( "k1", "v1" );
    }
}
