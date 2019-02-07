package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.AbuExecutionContext;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class AbuServerImpl implements AbuServer {

    private static final Logger logger = LoggerFactory.getLogger( AbuServerImpl.class );

    private final AbuExecutionContext context;
    private Server server;

    public AbuServerImpl( AbuExecutionContext context ) {
        this.context = context;
    }

    @Override public void run() {
        showInfo();

        server = new Server( context.getConfig().port() );
        server.setHandler( new JettyHandlerImpl( context ) );
        uncheck( () -> server.start() );
    }

    @Override public void stop() {
        if ( server != null ) {
            uncheck( () -> server.stop() );
        }
    }

    private static final char ESC = 27;
    private static final String ANSI_YELLOW = ESC + "[33;m";
    private static final String ANSI_REST = ESC + "[0m";

    private static void showInfo() {
        logger.info( "\n\n{}{}{}  {}\n", ANSI_YELLOW, Information.AA, ANSI_REST, Information.VERSION );
    }
}
