package rip.deadcode.abukuma3.handler.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.handler.Handlers;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Responses;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rip.deadcode.izvestia.Core.expect;


class TypeBasedDispatcherImplTest {

    @Test
    void test() {

        Handler handler = Handlers.byContentType()
                                  .json( ( ctx, r ) -> Responses.create( "json" ) )
                                  .xml( ( ctx, r ) -> Responses.create( "xml" ) )
                                  .text( ( ctx, r ) -> Responses.create( "text" ) )
                                  .fallback( ( ctx, r ) -> Responses.create( "fallback" ) );

        ExecutionContext mockCtx = mock( ExecutionContext.class );
        Request mockReq = mock( Request.class );
        RequestHeader mockHeader = mock( RequestHeader.class );

        when( mockReq.header() ).thenReturn( mockHeader );
        when( mockHeader.contentType() ).thenReturn(
                "application/json",
                "application/xml",
                "plain/text",
                "application/octet-stream"
        );

        String result = (String) handler.handle( mockCtx, mockReq ).body();
        assertThat( result ).isEqualTo( "json" );
        result = (String) handler.handle( mockCtx, mockReq ).body();
        assertThat( result ).isEqualTo( "xml" );
        result = (String) handler.handle( mockCtx, mockReq ).body();
        assertThat( result ).isEqualTo( "text" );
        result = (String) handler.handle( mockCtx, mockReq ).body();
        assertThat( result ).isEqualTo( "fallback" );
    }

    @Test
    void testFallback() {

        ExecutionContext mockCtx = mock( ExecutionContext.class );
        Request mockReq = mock( Request.class );
        RequestHeader mockHeader = mock( RequestHeader.class );

        when( mockReq.header() ).thenReturn( mockHeader );
        when( mockHeader.contentType() ).thenReturn( "application/json" );

        expect( () -> {
            Handlers.byContentType().handle( mockCtx, mockReq );
        } ).throwsException( e -> {
            assertThat( e ).hasMessageThat()
                           .isEqualTo( "A fallback handler is not specified for the content type \"application/json\"" );
        } );
    }
}
