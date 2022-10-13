package rip.deadcode.abukuma3.test;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Response;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;


public final class TestServer implements Server {

    private final HandlerAdapter<Request<?>, TestResultHolder> adapter;

    private TestServer( ExecutionContext context ) {
        this.adapter = new HandlerAdapter<>( context ) {

            @Override protected String pathString( Request<?> originalRequest ) {
                return originalRequest.urlString();
            }

            @Override protected RequestHeader createHeader(
                    ExecutionContext context, PersistentList<String> urlPaths, Request<?> originalRequest ) {
                return originalRequest.header();
            }

            @SuppressWarnings( "unchecked" )
            @Override
            protected <T> Request<T> createRequest(
                    Function<InputStream, T> body,
                    RequestHeader header,
                    Request<?> originalRequest,
                    TestResultHolder originalResponse,
                    PersistentMap<String, String> pathParams ) {

                return (Request<T>) originalRequest;
            }

            @Override protected void submitResponse(
                    ExecutionContext context,
                    Response response,
                    RenderingResult renderingResult,
                    Request<?> originalRequest,
                    TestResultHolder originalResponse ) throws Exception {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                renderingResult.rendering().accept( baos );
                byte[] body = baos.toByteArray();
                originalResponse.result = new TestResult() {

                    @Override public Response response() {
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

    public static TestServer create( ExecutionContext context ) {
        return new TestServer( context );
    }

    public TestResult send( Request request ) {

        TestResultHolder holder = new TestResultHolder();
        adapter.handle( request, holder );
        return checkNotNull( holder.result );
    }
}
