package rip.deadcode.abukuma3.parser.internal;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.test.internal.utils.TestUtils.str2is;


class StringParserTest {

    @Test
    void test() {

        String param = "test";

        String result1 = new StringParser().parse( String.class, str2is( param ), null );
        assertThat( result1 ).isEqualTo( param );

        CharSequence result2 = new StringParser().parse( CharSequence.class, str2is( param ), null );
        assertThat( result2 ).isEqualTo( param );
    }
}