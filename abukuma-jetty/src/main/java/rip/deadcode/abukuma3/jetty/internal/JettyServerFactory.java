package rip.deadcode.abukuma3.jetty.internal;


import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.ServerFactory;


public final class JettyServerFactory implements ServerFactory {
    @Override public Server provide( ExecutionContext context ) {
        return new JettyServer( context );
    }
}
