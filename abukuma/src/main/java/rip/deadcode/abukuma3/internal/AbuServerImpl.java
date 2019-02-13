package rip.deadcode.abukuma3.internal;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.value.AbuConfig;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.also;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class AbuServerImpl implements AbuServer {

    private static final Logger logger = LoggerFactory.getLogger( AbuServerImpl.class );

    private final AbuExecutionContext context;
    private Server server;

    public AbuServerImpl( AbuExecutionContext context ) {
        this.context = context;
    }

    @Override public void run() {
        logger.info( Information.INFO_STRING );

        AbuConfig config = context.config();

        HttpConfiguration jettyConfig = also( new HttpConfiguration(), c -> {
            c.setSendServerVersion( false );
        } );

        HttpConnectionFactory connectionFactory = new HttpConnectionFactory( jettyConfig );

        QueuedThreadPool threadPool = new QueuedThreadPool( config.maxThreads(), config.minThreads() );

        server = new Server( threadPool );
        ServerConnector connector = also( new ServerConnector( server, connectionFactory ), c -> {
            c.setPort( config.port() );
        } );
        server.setConnectors( new Connector[] { connector } );

        server.setHandler( new JettyHandlerImpl( context ) );
        uncheck( () -> server.start() );
    }

    @Override public void stop() {
        if ( server != null ) {
            uncheck( () -> server.stop() );
        }
    }
}
