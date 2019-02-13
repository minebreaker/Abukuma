package rip.deadcode.abukuma3.internal.utils;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.also;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


class MoreMoreObjectsTest {

    @Test
    void testCoalesce() {
        assertThat( coalesce( "foo", () -> "bar" ).orElse( "buz" ) ).isEqualTo( "foo" );
        assertThat( coalesce( null, () -> "bar" ).orElse( "buz" ) ).isEqualTo( "bar" );
        assertThat( coalesce( null, () -> null ).orElse( "buz" ) ).isEqualTo( "buz" );
    }

    @Test
    void testAlso() {

        class Mutable {
            private String field;

            public String getField() {
                return field;
            }

            public void setField( String field ) {
                this.field = field;
            }
        }

        Mutable result = also( new Mutable(), m -> {
            m.setField( "foo" );
        } );

        assertThat( result.getField() ).isEqualTo( "foo" );
    }
}