package rip.deadcode.abukuma3.netty.internal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Server;


public final class NettyServer implements Server {

    private final ExecutionContext context;

    private EventLoopGroup parentGroup;
    private EventLoopGroup workerGroup;

    public NettyServer( ExecutionContext context ) {
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
                    .bind( context.config().port() ).syncUninterruptibly()
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
