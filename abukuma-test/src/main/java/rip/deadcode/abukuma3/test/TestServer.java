package rip.deadcode.abukuma3.test;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public final class TestServer implements AbuServer {

    private final HandlerAdapter<AbuRequest, TestResultHolder> adapter;

    private TestServer( AbuExecutionContext context ) {
        this.adapter = new HandlerAdapter<AbuRequest, TestResultHolder>( context ) {

            @Override protected AbuRequestHeader createHeader(
                    AbuExecutionContext context, AbuRequest originalRequest ) {
                return originalRequest.header();
            }

            @Override protected AbuRequest createRequest(
                    AbuRequestHeader header,
                    AbuRequest originalRequest,
                    TestResultHolder originalResponse,
                    Map<String, String> pathParams ) {

                return originalRequest;
            }

            @Override protected void submitResponse(
                    AbuExecutionContext context,
                    AbuResponse response,
                    AbuRenderingResult renderingResult,
                    AbuRequest originalRequest,
                    TestResultHolder originalResponse ) throws Exception {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                renderingResult.rendering().accept( baos );
                byte[] body = baos.toByteArray();
                originalResponse.result = new TestResult() {

                    @Override public AbuResponse response() {
                        return response;
                    }

                    @Override public byte[] body() {
                        return body;
                    }

                    @Override public String bodyAsString( Charset charset ) {
                        return new String( body, charset );
                    }
                };
            }
        };
    }

    private static final class TestResultHolder {
        private TestResult result;
    }

    @Override public void run() {
        // NOP
    }

    @Override public void stop() {
        // NOP
    }

    public static TestServer create( AbuExecutionContext context ) {
        return new TestServer( context );
    }

    public TestResult send( AbuRequest request ) {

        TestResultHolder holder = new TestResultHolder();
        adapter.handle( request, holder );
        return checkNotNull( holder.result );
    }
}
