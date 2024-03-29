package rip.deadcode.abukuma3.collection;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.collection.internal.PersistentSetImpl;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;


public final class PersistentSetImplTest {

    @Test
    void testSet() {

        var param = new PersistentSetImpl<String>().set( "foo" );

        assertThat( param ).containsExactly( "foo" );
    }

    @Test
    void testMerge() {

        var param = new PersistentSetImpl<>( Set.of( "foo", "bar" ) )
                .merge( Set.of( "bar", "buz" ) );

        assertThat( param ).containsExactly( "foo", "bar", "buz" );
    }

    @Test
    void testMutable() {

        var param = new PersistentSetImpl<>( Set.of( "foo" ) ).mutable();
        param.add( "bar" );

        assertThat( param ).containsExactly( "foo", "bar" );
    }
}
