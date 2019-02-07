package rip.deadcode.abukuma3.gson.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.gson.JsonBody;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.gson.internal.GsonUtils.any;
import static rip.deadcode.abukuma3.gson.internal.GsonUtils.isAnnotatedBy;

class GsonUtilsTest {

    @Test
    void testAnnotatedBy() {
        @JsonBody
        class Foo {}
        class Bar extends Foo {}
        class Buz {}

        assertThat( isAnnotatedBy( Foo.class, JsonBody.class ) ).isTrue();
        assertThat( isAnnotatedBy( Bar.class, JsonBody.class ) ).isTrue();
        assertThat( isAnnotatedBy( Buz.class, JsonBody.class ) ).isFalse();
    }

    @Test
    void testAny() {
        String[] params = new String[] { "foo", "bar", "buz" };
        assertThat( any( params, e -> e.startsWith( "f" ) ) ).isTrue();
        assertThat( any( params, e -> e.startsWith( "b" ) ) ).isTrue();
        assertThat( any( params, e -> e.equals( "foo" ) ) ).isTrue();
        assertThat( any( params, e -> e.equals( "none" ) ) ).isFalse();
    }
}