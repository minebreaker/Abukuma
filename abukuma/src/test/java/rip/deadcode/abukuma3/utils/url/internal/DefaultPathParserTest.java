package rip.deadcode.abukuma3.utils.url.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.utils.url.UrlPathParseResult;
import rip.deadcode.abukuma3.utils.url.UrlPathParser;

import static com.google.common.truth.Truth.assertThat;


class DefaultPathParserTest {

    @Test
    public void test() {
        UrlPathParser parser = new DefaultPathParser();

        UrlPathParseResult.Success result = ( (UrlPathParseResult.Success) parser.parse( "/foo/bar/buz" ) );
        assertThat( result.result() ).containsExactly( "foo", "bar", "buz" ).inOrder();

        result = ( (UrlPathParseResult.Success) parser.parse( "/foo/bar/buz?q" ) );
        assertThat( result.result() ).containsExactly( "foo", "bar", "buz" ).inOrder();

        result = ( (UrlPathParseResult.Success) parser.parse( "/" ) );
        assertThat( result.result() ).containsExactly( "" ).inOrder();

        result = ( (UrlPathParseResult.Success) parser.parse( "///foo/" ) );
        assertThat( result.result() ).containsExactly( "", "", "foo", "" ).inOrder();

        result = ( (UrlPathParseResult.Success) parser.parse( "*" ) );
        assertThat( result.result() ).containsExactly( "*" ).inOrder();
    }
}
