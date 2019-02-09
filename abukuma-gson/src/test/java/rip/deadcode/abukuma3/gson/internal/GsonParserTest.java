package rip.deadcode.abukuma3.gson.internal;

import org.eclipse.jetty.server.Request;
import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.gson.AbuGson;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GsonParserTest {

    @Test
    void test() throws IOException {

        InputStream is = new ByteArrayInputStream( "{\"foo\":\"bar\"}".getBytes( StandardCharsets.UTF_8 ) );
        Request r = mock( Request.class );
        when( r.getContentType() ).thenReturn( "application/json; charset=utf-8" );

        SamplePojo result = (SamplePojo) AbuGson.parser().parse( SamplePojo.class, is, new AbuRequestHeader( null, r ) );

        assertThat( result ).isNotNull();
        assertThat( result.getFoo() ).isEqualTo( "bar" );
    }
}
