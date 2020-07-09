package rip.deadcode.abukuma3.handler.internal;

import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.handler.AbuHandlers;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponses;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rip.deadcode.izvestia.Core.expect;


class TypeBasedDispatcherImplTest {

    @Test
    void test() {

        AbuHandler handler = AbuHandlers.byContentType()
                                        .json( ( ctx, r ) -> AbuResponses.create( "json" ) )
                                        .xml( ( ctx, r ) -> AbuResponses.create( "xml" ) )
                                        .text( ( ctx, r ) -> AbuResponses.create( "text" ) )
                                        .fallback( ( ctx, r ) -> AbuResponses.create( "fallback" ) );

        AbuExecutionContext mockCtx = mock( AbuExecutionContext.class );
        AbuRequest mockReq = mock( AbuRequest.class );
        AbuRequestHeader mockHeader = mock( AbuRequestHeader.class );

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

        AbuExecutionContext mockCtx = mock( AbuExecutionContext.class );
        AbuRequest mockReq = mock( AbuRequest.class );
        AbuRequestHeader mockHeader = mock( AbuRequestHeader.class );

        when( mockReq.header() ).thenReturn( mockHeader );
        when( mockHeader.contentType() ).thenReturn( "application/json" );

        expect( () -> {
            AbuHandlers.byContentType().handle( mockCtx, mockReq );
        } ).throwsException( e -> {
            assertThat( e ).hasMessageThat()
                           .isEqualTo( "A fallback handler is not specified for the content type \"application/json\"" );
        } );
    }
}
