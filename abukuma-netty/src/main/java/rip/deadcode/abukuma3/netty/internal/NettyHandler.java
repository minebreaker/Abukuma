package rip.deadcode.abukuma3.netty.internal;

import io.netty.buffer.ByteBuf;
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
import rip.deadcode.abukuma3.AbuExecutionContext;

import java.nio.charset.StandardCharsets;


public final class NettyHandler extends ChannelInitializer<SocketChannel> {

    private final AbuExecutionContext context;
    private final EventExecutorGroup executors;

    public NettyHandler( AbuExecutionContext context ) {
        this.context = context;

        this.executors = new DefaultEventExecutorGroup( 16 );
    }

    @Override
    protected void initChannel( SocketChannel ch ) {
        ch.pipeline()
          .addLast( new HttpRequestDecoder() )
          .addLast( new HttpResponseEncoder() )
          .addLast( new HttpContentCompressor() )
          .addLast( executors, new Handler() );
    }

    private static final class Handler extends SimpleChannelInboundHandler<Object> {
        @Override
        protected void channelRead0( ChannelHandlerContext ctx, Object msg ) throws Exception {

            System.out.println( "=========" );

            if ( msg instanceof HttpRequest ) {
                System.out.println( msg );
            }

            if ( msg instanceof HttpContent ) {
                System.out.println( msg );

                if ( msg instanceof LastHttpContent ) {

                    ByteBuf body = Unpooled.copiedBuffer( "hello from netty", StandardCharsets.US_ASCII );

                    FullHttpResponse response = new DefaultFullHttpResponse(
                            HttpVersion.HTTP_1_1,
                            HttpResponseStatus.OK,
                            body
                    );

                    response.headers().add( HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes() );
                    response.headers().add( HttpHeaderNames.CONTENT_TYPE, "text/plain" );

                    ctx.writeAndFlush( response ).addListener( ChannelFutureListener.CLOSE );
                }
            }
        }
    }
}
