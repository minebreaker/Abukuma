package rip.deadcode.abukuma3.netty.internal;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
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
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.netty.internal.value.NettyRequest;
import rip.deadcode.abukuma3.netty.internal.value.NettyRequestHeader;
import rip.deadcode.abukuma3.renderer.AbuRenderingResult;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.net.ssl.KeyManagerFactory;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class NettyHandler extends ChannelInitializer<SocketChannel> {

    private final HandlerAdapter<RequestAndContent, ChannelHandlerContext> adapter;
    private final EventExecutorGroup executors;

    public NettyHandler( AbuExecutionContext context ) {

        this.adapter = new HandlerAdapter<RequestAndContent, ChannelHandlerContext>( context ) {

            @Override
            protected AbuRequestHeader createHeader( AbuExecutionContext context, RequestAndContent originalRequest ) {
                return new NettyRequestHeader( context, originalRequest );
            }

            @Override
            protected AbuRequest createRequest(
                    AbuExecutionContext context,
                    AbuRequestHeader header,
                    RequestAndContent originalRequest,
                    ChannelHandlerContext originalResponse,
                    Map<String, String> pathParams ) {
                return new NettyRequest( context, header, originalRequest, originalResponse, pathParams );
            }

            @Override
            protected void submitResponse(
                    AbuExecutionContext context,
                    AbuResponse response,
                    AbuRenderingResult renderingResult,
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
        KeyStore keyStore = uncheck(() -> KeyStore.getInstance("JKS"));
        KeyManagerFactory keyManagerFactory = uncheck(() -> KeyManagerFactory.getInstance("X509"));
        uncheck(() -> keyManagerFactory.init(keyStore, "".toCharArray()));
        SslContext sslCtx = uncheck(() ->
                SslContextBuilder
                .forServer(keyManagerFactory)
                .build());

        ch.pipeline()
          .addLast( new SslHandler(sslCtx.newEngine(ch.alloc())))
          .addLast( new HttpRequestDecoder() )
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

                adapter.handle( new RequestAndContent( request, content ), ctx );
            } else {
                throw new IllegalStateException( "TODO not supported yet" );
            }
        }
    }
}
