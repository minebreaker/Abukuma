package rip.deadcode.abukuma3.internal;


import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.ServerFactory;


public final class JettyServerFactory implements ServerFactory {
    @Override public AbuServer provide( AbuExecutionContext context ) {
        return new AbuServerImpl( context );
    }
}
