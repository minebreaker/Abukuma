package rip.deadcode.abukuma3.netty.internal;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.netty.internal.value.NettyRequest;
import rip.deadcode.abukuma3.netty.internal.value.NettyRequestHeader;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Response;

import javax.net.ssl.KeyManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class NettyHandler extends ChannelInitializer<SocketChannel> {

    private final ExecutionContext context;
    private final HandlerAdapter<RequestAndContent, ChannelHandlerContext> adapter;
    private final EventExecutorGroup executors;

    public NettyHandler( ExecutionContext context ) {

        this.context = context;
        this.adapter = new HandlerAdapter<>( context ) {

            @Override protected String pathString( RequestAndContent originalRequest ) {
                return originalRequest.request.uri();
            }

            @Override
            protected RequestHeader createHeader(
                    ExecutionContext context, PersistentList<String> urlPaths, RequestAndContent originalRequest ) {
                return new NettyRequestHeader( context, urlPaths, originalRequest );
            }

            @Override
            protected <T> Request<T> createRequest(
                    Function<InputStream, T> is2body,
                    RequestHeader header,
                    RequestAndContent originalRequest,
                    ChannelHandlerContext originalResponse,
                    PersistentMap<String, String> pathParams ) {

                ByteBuf buf = originalRequest.content.content();
                byte[] arr = new byte[buf.readableBytes()];
                buf.getBytes( buf.readerIndex(), arr );

                try ( InputStream is = new ByteArrayInputStream( arr ) ) {
                    return new NettyRequest<>(
                            is2body.apply( is ),
                            header,
                            originalRequest,
                            originalResponse,
                            pathParams
                    );
                } catch ( IOException e ) {
                    throw new RuntimeException( e );
                }
            }

            @Override
            protected void submitResponse(
                    ExecutionContext context,
                    Response response,
                    RenderingResult renderingResult,
                    RequestAndContent originalRequest,
                    ChannelHandlerContext originalResponse ) throws Exception {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                renderingResult.rendering().accept( baos );

                FullHttpResponse nettyResponse = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.valueOf( response.status() ),
                        Unpooled.copiedBuffer( baos.toByteArray() )
                );

                nettyResponse.headers().add( HttpHeaderNames.CONTENT_LENGTH, nettyResponse.content().readableBytes() );
                nettyResponse.headers().add( HttpHeaderNames.CONTENT_TYPE, response.header().contentType() );

                // TODO respect keep-alive
                originalResponse.writeAndFlush( nettyResponse ).addListener( ChannelFutureListener.CLOSE );
            }
        };

        this.executors = new DefaultEventExecutorGroup( 16 );
    }

    public static final class RequestAndContent {
        public final HttpRequest request;
        public final HttpContent content;

        private RequestAndContent( HttpRequest request, HttpContent content ) {
            this.request = request;
            this.content = content;
        }
    }

    @Override
    protected void initChannel( SocketChannel ch ) {
        KeyStore keyStore = uncheck( () -> KeyStore.getInstance( "JKS" ) );
        KeyManagerFactory keyManagerFactory = uncheck( () -> KeyManagerFactory.getInstance( "X509" ) );
        uncheck( () -> keyManagerFactory.init( keyStore, "".toCharArray() ) );
        SslContext sslCtx = uncheck( () -> SslContextBuilder.forServer( keyManagerFactory ).build() );

        ChannelPipeline cp = ch.pipeline();
        if ( context.config().ssl() ) {
            cp.addLast( new SslHandler( sslCtx.newEngine( ch.alloc() ) ) );
        }

        cp.addLast( new HttpRequestDecoder() )
          .addLast( new HttpResponseEncoder() )
          .addLast( new HttpContentCompressor() )
          .addLast( executors, new Handler( adapter ) );
    }

    private static final class Handler extends SimpleChannelInboundHandler<Object> {

        private final HandlerAdapter<RequestAndContent, ChannelHandlerContext> adapter;
        private HttpRequest request;

        private Handler( HandlerAdapter<RequestAndContent, ChannelHandlerContext> adapter ) {
            this.adapter = adapter;
        }

        @Override
        protected void channelRead0( ChannelHandlerContext ctx, Object msg ) throws Exception {

            if ( msg instanceof HttpRequest ) {
                this.request = (HttpRequest) msg;
            }

            if ( msg instanceof LastHttpContent ) {
                checkNotNull( request );
                HttpContent content = (HttpContent) msg;

                // FIXME: handling should be async
                adapter.handle( new RequestAndContent( request, content ), ctx );
            } else {
                throw new IllegalStateException( "TODO not supported yet" );
            }
        }
    }
}
