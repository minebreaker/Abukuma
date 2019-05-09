package rip.deadcode.abukuma3.netty.internal;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.ServerFactory;


public final class NettyServerFactory implements ServerFactory {
    @Override public AbuServer provide( AbuExecutionContext context ) {
        return new NettyServer( context );
    }
}
