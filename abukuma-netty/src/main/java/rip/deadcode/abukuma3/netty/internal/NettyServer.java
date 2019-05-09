package rip.deadcode.abukuma3.netty.internal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.AbuServer;


public final class NettyServer implements AbuServer {

    private final AbuExecutionContext context;

    private EventLoopGroup parentGroup;
    private EventLoopGroup workerGroup;

    public NettyServer( AbuExecutionContext context ) {
        this.context = context;
    }

    public static void main( String[] args ) {
        new NettyServer( null ).run();
    }

    @Override public void run() {
        parentGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group( parentGroup, workerGroup )
                    .channel( NioServerSocketChannel.class )
                    .childHandler( new NettyHandler( context ) )
                    .bind( 8080 ).syncUninterruptibly()
                    .channel().closeFuture().syncUninterruptibly();

        } catch ( RuntimeException e ) {
            stop();
        }
    }

    @Override public void stop() {
        if ( workerGroup != null ) {
            workerGroup.shutdownGracefully().syncUninterruptibly();
        }
        if ( parentGroup != null ) {
            parentGroup.shutdownGracefully().syncUninterruptibly();
        }
    }
}
