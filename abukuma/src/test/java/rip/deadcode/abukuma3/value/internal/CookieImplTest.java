package rip.deadcode.abukuma3.value.internal;


import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.utils.url.internal.CookieImpl;
import rip.deadcode.abukuma3.value.Cookie;

import static com.google.common.truth.Truth.assertThat;


public final class CookieImplTest {

    private static final Cookie param = new CookieImpl( "name", "value" );

    @Test
    void test() {
        assertThat( param.name() ).isEqualTo( "name" );
        assertThat( param.value() ).isEqualTo( "value" );
        assertThat( param.maxAge().isPresent() ).isFalse();
        assertThat( param.domain().isPresent() ).isFalse();
        assertThat( param.path().isPresent() ).isFalse();
        assertThat( param.secure() ).isFalse();
        assertThat( param.httpOnly() ).isFalse();
        assertThat( param.sameSite().isPresent() ).isFalse();
    }

    @Test
    void testToString() {
        Cookie param = new CookieImpl( "name", "value", 3600, "domain", "/path", true, true, null );

        assertThat( param.toString() ).isEqualTo(
                "name=value; Max-Age=3600; Domain=domain; Path=/path; Secure; HttpOnly" );
    }
}
