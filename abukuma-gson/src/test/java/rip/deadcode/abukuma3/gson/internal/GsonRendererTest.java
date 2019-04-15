package rip.deadcode.abukuma3.gson.internal;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.gson.AbuGson;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class GsonRendererTest {

    @Test
    void test() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        SamplePojo param = new SamplePojo();
        param.setFoo( "bar" );

        AbuExecutionContext context = mock( AbuExecutionContext.class );
        when( context.get( Gson.class ) ).thenReturn( new Gson() );

        AbuRenderingResult response = AbuGson.renderer().render( context, AbuResponses.create( param ) );

        assertThat( response ).isNotNull();

        AbuResponse rendered = response.modifying().get();
        assertThat( rendered.header().contentType() ).isEqualTo( "application/json" );
        response.rendering().accept( os );
        assertThat( new String( os.toByteArray(), StandardCharsets.UTF_8 ) ).isEqualTo( "{\"foo\":\"bar\"}" );
    }
}
