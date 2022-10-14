package rip.deadcode.abukuma3.parser.internal;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.internal.utils.IoStreams.is2str;
import static rip.deadcode.abukuma3.internal.utils.IoStreams.str2is;


class InputStreamParserTest {

    @Test
    void test() {

        String param = "test";

        InputStream is = new InputStreamParser().parse( null, InputStream.class, str2is( param ), null );
        assertThat( is2str( is ) ).isEqualTo( param );
    }
}
