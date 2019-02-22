package rip.deadcode.abukuma3.gson.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.gson.AbuGson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;


class GsonRendererTest {

    @Test
    void test() throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        SamplePojo param = new SamplePojo();
        param.setFoo( "bar" );

        boolean result = AbuGson.renderer().render( os, param );

        assertThat( result ).isTrue();
        assertThat( new String( os.toByteArray(), StandardCharsets.UTF_8 ) ).isEqualTo( "{\"foo\":\"bar\"}" );
    }
}