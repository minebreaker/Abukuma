package rip.deadcode.abukuma3.gson.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.gson.AbuGson;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;


class GsonRendererTest {

    @Test
    void test() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        SamplePojo param = new SamplePojo();
        param.setFoo( "bar" );

        AbuRenderingResult response = AbuGson.renderer().render( AbuResponse.create( param ) );

        assertThat( response ).isNotNull();

        AbuResponse rendered = response.modifying().get();
        assertThat( rendered.header().contentType() ).isEqualTo( "application/json" );
        response.rendering().accept( os );
        assertThat( new String( os.toByteArray(), StandardCharsets.UTF_8 ) ).isEqualTo( "{\"foo\":\"bar\"}" );
    }
}
