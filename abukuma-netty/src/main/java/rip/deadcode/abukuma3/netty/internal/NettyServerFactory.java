package rip.deadcode.abukuma3.netty.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.ServerFactory;


public final class NettyServerFactory implements ServerFactory {
    @Override public Server provide( ExecutionContext context ) {
        return new NettyServer( context );
    }
}
