package rip.deadcode.abukuma3;

import org.eclipse.jetty.server.Server;

import static rip.deadcode.akashi.util.Uncheck.uncheck;

public final class AbuServerImpl implements AbuServer {

    private final AbuConfig config;
    private final AbuRouter router;

    public AbuServerImpl( AbuConfig config, AbuRouter router ) {
        this.config = config;
        this.router = router;
    }

    /**
     * Runs the server.
     * This method blocks the thread.
     */
    public void run() {
        Server server = new Server( config.getPort() );
        server.setHandler( new HandlerImpl( router, new DefaultExceptionHandler() ) );
        uncheck( () -> {
            server.start();
            server.join();
        } );
    }
}
