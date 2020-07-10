package rip.deadcode.abukuma3.jetty.internal;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.value.Config;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.also;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class JettyServer implements Server {

    private final ExecutionContext context;
    private org.eclipse.jetty.server.Server server;

    public JettyServer( ExecutionContext context ) {
        this.context = context;
    }

    @Override public void run() {

        Config config = context.config();

        HttpConfiguration jettyConfig = also( new HttpConfiguration(), c -> {
            c.setSendServerVersion( false );
        } );

        HttpConnectionFactory connectionFactory = new HttpConnectionFactory( jettyConfig );

        QueuedThreadPool threadPool = new QueuedThreadPool( config.maxThreads(), config.minThreads() );

        server = also( new org.eclipse.jetty.server.Server( threadPool ), server -> {
            ServerConnector connector = also( new ServerConnector( server, connectionFactory ), c -> {
                c.setPort( config.port() );
            } );

            server.setConnectors( new Connector[] { connector } );
            server.setHandler( new JettyHandler( context ) );
        } );
        uncheck( () -> server.start() );
    }

    @Override public void stop() {
        if ( server != null ) {
            uncheck( () -> server.stop() );
        }
    }
}
