package rip.deadcode.abukuma3.router.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.router.PathParser;
import rip.deadcode.abukuma3.router.PathParsingResult;


class DefaultPathParserTest {

    private PathParser parser = new DefaultPathParser();

    @Test
    public void test() {

        PathParsingResult result = parser.parse( "/foo/bar/buz" );
//        assertThat(result.paths()).containsExactly( "/", "foo", "/", "bar", "/", "buz" ).inOrder();
//        assertThat( result.rest() ).isEmpty();


    }

}
