package rip.deadcode.abukuma3.internal;

import com.typesafe.config.ConfigFactory;
import rip.deadcode.abukuma3.ConfigSpec;
import rip.deadcode.abukuma3.ServerSpec;
import rip.deadcode.abukuma3.value.Config;
import rip.deadcode.abukuma3.value.internal.ConfigImpl;


public final class ConfigSpecImpl implements ConfigSpec {

    private static final String ROOT_PATH = "abukuma";

    @Override public ServerSpec useDefault() {
        return new ServerSpecImpl( convert( ConfigFactory.load() ) );
    }

    @Override public ServerSpec useConfig( com.typesafe.config.Config config ) {
        return new ServerSpecImpl( convert( config ) );
    }

    @Override public ServerSpec useConfigWithDefault( com.typesafe.config.Config config ) {
        var mergedConfig = config.withFallback( ConfigFactory.defaultReference() );
        return new ServerSpecImpl( convert( mergedConfig ) );
    }

    private static Config convert( com.typesafe.config.Config rootConfig ) {

        rootConfig.checkValid( ConfigFactory.defaultReference(), ROOT_PATH );

        var config = rootConfig.getConfig( ROOT_PATH );

        // TODO: friendly validations

        int port = config.getInt( "port" );

        int maxThreads = config.getInt( "maxThreads" );
        int minThreads = config.getInt( "minThreads" );

        boolean ssl = config.getBoolean( "ssl" );

        return new ConfigImpl(
                port,
                maxThreads,
                minThreads,
                ssl,
                config
        );
    }
}
