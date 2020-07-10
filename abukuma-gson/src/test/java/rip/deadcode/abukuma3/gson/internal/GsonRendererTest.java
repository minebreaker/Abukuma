package rip.deadcode.abukuma3.gson.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.gson.Gson;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;

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

        ExecutionContext context = mock( ExecutionContext.class );
        when( context.get( com.google.gson.Gson.class ) ).thenReturn( new com.google.gson.Gson() );

        RenderingResult response = Gson.renderer().render( context, Responses.create( param ) );

        assertThat( response ).isNotNull();

        Response rendered = response.modifying().get();
        assertThat( rendered.header().contentType() ).isEqualTo( "application/json" );
        response.rendering().accept( os );
        assertThat( new String( os.toByteArray(), StandardCharsets.UTF_8 ) ).isEqualTo( "{\"foo\":\"bar\"}" );
    }
}
