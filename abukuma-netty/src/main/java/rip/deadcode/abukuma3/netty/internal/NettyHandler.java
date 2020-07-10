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
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.internal.HandlerAdapter;
import rip.deadcode.abukuma3.netty.internal.value.NettyRequest;
import rip.deadcode.abukuma3.netty.internal.value.NettyRequestHeader;
import rip.deadcode.abukuma3.renderer.RenderingResult;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Response;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public final class NettyHandler extends ChannelInitializer<SocketChannel> {

    private final HandlerAdapter<RequestAndContent, ChannelHandlerContext> adapter;
    private final EventExecutorGroup executors;

    public NettyHandler( ExecutionContext context ) {

        this.adapter = new HandlerAdapter<RequestAndContent, ChannelHandlerContext>( context ) {

            @Override
            protected RequestHeader createHeader( ExecutionContext context, RequestAndContent originalRequest ) {
                return new NettyRequestHeader( context, originalRequest );
            }

            @Override
            protected Request createRequest(
                    RequestHeader header,
                    RequestAndContent originalRequest,
                    ChannelHandlerContext originalResponse,
                    Map<String, String> pathParams ) {
                return new NettyRequest( context, header, originalRequest, originalResponse, pathParams );
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
        ch.pipeline()
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
