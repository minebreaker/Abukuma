package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;


class PersistentMapImplTest {

    @Test
    void testMayGet() {
        assertThat( PersistentMapImpl.create() ).isEmpty();

        assertThat( PersistentMapImpl.create().set( "k", "v" ).mayGet( "k" ) ).hasValue( "v" );
        assertThat( PersistentMapImpl.create().set( "k", "v" ).mayGet( "nothing" ) ).isEmpty();
    }

    @Test
    void testSet() {

        PersistentMapImpl<String, String> param = PersistentMapImpl.<String, String>create()
                .set( "k1", "v1" ).set( "k2", "v2" );
        assertThat( param ).containsExactly( "k1", "v1", "k2", "v2" );
        assertThat( param.set( "k2", "replaced" ) ).containsExactly( "k1", "v1", "k2", "replaced" );
    }

    @Test
    void testDelete() {

        PersistentMapImpl<String, String> param = PersistentMapImpl.<String, String>create()
                .set( "k1", "v1" ).set( "k2", "v2" );
        assertThat( param.delete( "k2" ) ).containsExactly( "k1", "v1" );
    }

    @Test
    void testMutable() {

        Map<String, String> param = PersistentMapImpl.<String, String>create().set( "k1", "v1" ).mutable();
        param.put( "k1", "replaced" );

        assertThat( param ).containsExactly( "k1", "replaced" );
    }
}
