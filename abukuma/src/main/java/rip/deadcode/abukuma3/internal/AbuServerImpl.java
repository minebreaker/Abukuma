package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Server;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.ExecutionContext;

import static rip.deadcode.akashi.util.Uncheck.uncheck;

public final class AbuServerImpl implements AbuServer {

    private final ExecutionContext context;
    private Server server;

    public AbuServerImpl( ExecutionContext context ) {
        this.context = context;
    }

    @Override public void run() {
        server = new Server( context.getConfig().port() );
        server.setHandler( new HandlerImpl( context ) );
        uncheck( () -> server.start() );
    }

    @Override public void stop() {
        if ( server != null ) {
            uncheck( () -> server.stop() );
        }
    }
}
