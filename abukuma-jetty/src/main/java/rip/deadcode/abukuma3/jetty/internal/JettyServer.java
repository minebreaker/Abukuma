package rip.deadcode.abukuma3.jetty.internal;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.AbuServer;
import rip.deadcode.abukuma3.value.AbuConfig;

import java.security.KeyStore;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.also;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class JettyServer implements AbuServer {

    private final AbuExecutionContext context;
    private Server server;

    public JettyServer( AbuExecutionContext context ) {
        this.context = context;
    }

    @Override public void run() {

        AbuConfig config = context.config();

        HttpConfiguration jettyConfig = also( new HttpConfiguration(), c -> {
            c.setSendServerVersion( false );
        } );

        SslContextFactory ssl = also( new SslContextFactory(), f -> {
            f.setKeyStore( KeyStore.getInstance( "" ) );
            f.setKeyStorePassword( "" );
        } );

        HttpConnectionFactory connectionFactory = new HttpConnectionFactory( jettyConfig );

        QueuedThreadPool threadPool = new QueuedThreadPool( config.maxThreads(), config.minThreads() );

        server = also( new Server( threadPool ), server -> {
            ServerConnector connector = also( new ServerConnector( server, ssl, connectionFactory ), c -> {
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
