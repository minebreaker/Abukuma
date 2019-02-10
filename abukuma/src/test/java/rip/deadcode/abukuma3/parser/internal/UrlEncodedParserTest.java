package rip.deadcode.abukuma3.parser.internal;

import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.parser.UrlEncoded;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.internal.utils.IoStreams.str2is;

class UrlEncodedParserTest {

    @Test
    void test() throws IOException {

        InputStream is = str2is( "foo=bar&hoge=piyo" );
        UrlEncoded result = new UrlEncodedParser().parse( UrlEncoded.class, is, null );

        assertThat( result ).isNotNull();
        assertThat( result.getValue( "foo" ) ).isEqualTo( "bar" );
        assertThat( result.getValue( "hoge" ) ).isEqualTo( "piyo" );
    }

    @Test
    void testParse() throws IOException {

        InputStream is = str2is( "foo=bar&hoge=piyo" );
        Multimap<String, String> result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "bar" );
        assertThat( result.get( "hoge" ) ).containsExactly( "piyo" );

        is = str2is( "foo=bar&foo=buz" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "bar", "buz" );

        is = str2is( "foo=bar+buz" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "bar buz" );

        is = str2is( "%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A=%E3%81%8B%E3%81%8D%E3%81%8F%E3%81%91%E3%81%93" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "あいうえお" ) ).containsExactly( "かきくけこ" );

        is = str2is( "foo&bar=buz" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "" );
        assertThat( result.get( "bar" ) ).containsExactly( "buz" );

        is = str2is( "foo=&bar=buz" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "" );
        assertThat( result.get( "bar" ) ).containsExactly( "buz" );

        is = str2is( "=foo&bar=buz" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "" ) ).containsExactly( "foo" );
        assertThat( result.get( "bar" ) ).containsExactly( "buz" );

        is = str2is( "=&foo=bar" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "" ) ).containsExactly( "" );
        assertThat( result.get( "foo" ) ).containsExactly( "bar" );

        is = str2is( "foo=bar=buz" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "bar=buz" );

        is = str2is( "&foo=bar" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.get( "foo" ) ).containsExactly( "bar" );

        is = str2is( "&&&" );
        result = UrlEncodedParser.parse( is );
        assertThat( result.isEmpty() ).isTrue();
    }
}
