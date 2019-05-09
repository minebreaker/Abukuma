package rip.deadcode.abukuma3.gson.internal;

import org.junit.jupiter.api.Test;

import java.io.IOException;


class GsonParserTest {

    @Test
    void test() throws IOException {

        // FIXME use MockRequest

//        InputStream is = new ByteArrayInputStream( "{\"foo\":\"bar\"}".getBytes( StandardCharsets.UTF_8 ) );
//        AbuExecutionContext c = mock( AbuExecutionContext.class );
//        when( c.get( Gson.class ) ).thenReturn( new Gson() );
//        Request r = mock( Request.class );
//        when( r.getContentType() ).thenReturn( "application/json; charset=utf-8" );
//
//        SamplePojo result = (SamplePojo) AbuGson.parser().parse( SamplePojo.class, is, new AbuRequestHeader( c, r ) );
//
//        assertThat( result ).isNotNull();
//        assertThat( result.getFoo() ).isEqualTo( "bar" );
    }
}
