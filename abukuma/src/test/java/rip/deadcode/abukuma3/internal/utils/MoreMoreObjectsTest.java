package rip.deadcode.abukuma3.internal.utils;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;

class MoreMoreObjectsTest {

    @Test
    void testCoalesce() {
        assertThat( coalesce( "foo", () -> "bar" ).orElse( "buz" ) ).isEqualTo( "foo" );
        assertThat( coalesce( null, () -> "bar" ).orElse( "buz" ) ).isEqualTo( "bar" );
        assertThat( coalesce( null, () -> null ).orElse( "buz" ) ).isEqualTo( "buz" );
    }
}