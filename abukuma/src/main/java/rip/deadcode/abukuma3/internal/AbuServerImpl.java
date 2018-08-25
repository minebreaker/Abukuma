package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Server;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.ExecutionContext;

import static rip.deadcode.akashi.util.Uncheck.uncheck;

public final class AbuServerImpl implements AbuServer {

    private final ExecutionContext context;

    public AbuServerImpl( ExecutionContext context ) {
        this.context = context;
    }

    /**
     * Runs the server.
     * This method blocks the thread.
     */
    public void run() {
        Server server = new Server( context.getConfig().getPort() );
        server.setHandler( new HandlerImpl( context ) );
        uncheck( () -> {
            server.start();
            server.join();
        } );
    }
}
