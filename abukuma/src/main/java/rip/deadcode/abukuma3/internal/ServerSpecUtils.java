package rip.deadcode.abukuma3.internal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Plugin;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.ServerFactory;
import rip.deadcode.abukuma3.ServerSpec;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.filter.Filters;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.parser.internal.EmptyParser;
import rip.deadcode.abukuma3.parser.internal.InputStreamParser;
import rip.deadcode.abukuma3.parser.internal.StringParser;
import rip.deadcode.abukuma3.parser.internal.UrlEncodedParser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.internal.CharSequenceRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.renderer.internal.PathRenderer;
import rip.deadcode.abukuma3.utils.internal.DefaultModule;
import rip.deadcode.abukuma3.value.Config;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.collection.PersistentCollectors.toPersistentList;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.reduceSequentially;


public final class ServerSpecUtils {

    private ServerSpecUtils() {
        throw new Error();
    }

    private static final Logger logger = LoggerFactory.getLogger( ServerSpecUtils.class );

    private static final PersistentList<Parser> defaultParsers = createList(
            new EmptyParser(),
            new UrlEncodedParser(),
            new StringParser(),
            new InputStreamParser()
    );

    private static final List<Renderer> defaultRenderers = createList(
            new PathRenderer(),
            new CharSequenceRenderer(),
            new InputStreamRenderer()
    );

    private static final Module defaultModule = new DefaultModule();

    public static Server build( ServerSpec spec ) {

        var plugins = getPlugins();
        var registryPluginsApplied = setupPluginConfigs( spec, plugins );
        var executionContext = createExecutionContext( registryPluginsApplied, spec, plugins );

        return createServer( executionContext );
    }

    private static PersistentList<Plugin> getPlugins() {
        var loader = ServiceLoader.load( Plugin.class );
        return loader.stream()
                     .map( p -> p.get() )
                     .collect( toPersistentList() );
    }

    private static Registry setupPluginConfigs( ServerSpec spec, List<Plugin> plugins ) {

        var serverConfig = spec.config();
        var registry = spec.registry()
                           // Add Config object
                           .setSingleton( Config.class, serverConfig )
                           .setSingleton( com.typesafe.config.Config.class, serverConfig.original() );

        var pluginConfigs = plugins.stream()
                                   .map( p -> p.configSpec() )
                                   .collect( toPersistentList() );
        return reduceSequentially( pluginConfigs, registry, ( r, configSpec ) -> {
            // TODO: error handling
            var pluginTypesafeConfig = serverConfig
                    .original()
                    .getConfig( "abukuma" )
                    .getConfig( configSpec.configPath() );
            var resolved = configSpec.resolve( pluginTypesafeConfig );
            return r.setSingleton( configSpec.configClass(), configSpec.configClass().cast( resolved ) );
        } );
    }

    private static ExecutionContext createExecutionContext( Registry registry, ServerSpec spec, List<Plugin> plugins ) {
        //noinspection OptionalGetWithoutIsPresent  should have at least one default implementations
        ExecutionContext context = new ExecutionContextImpl(
                registry,
                spec.config(),
                spec.parsers()
                    .concat( defaultParsers )
                    .stream().reduce( Parser::ifFailed ).get(),
                spec.renderers()
                    .concat( defaultRenderers )
                    .stream().reduce( Renderer::ifFailed ).get(),
                spec.filters().stream().reduce( Filter::then ).orElseGet( Filters::noop ),
                spec.router(),
                spec.exceptionHandler()
        );

        var pluginModules = plugins.stream().map( p -> p.module() ).collect( toPersistentList() );

        return reduceSequentially(
                spec.modules()
                    .concat( pluginModules )
                    .addLast( defaultModule ),
                context,
                ExecutionContext::applyModule
        );
    }

    private static Server createServer( ExecutionContext context ) {
        ServiceLoader<ServerFactory> loader = ServiceLoader.load( ServerFactory.class );
        Optional<String> requestedFactoryName = context.config().serverImplementation();

        if ( requestedFactoryName.isPresent() ) {
            String factoryName = requestedFactoryName.get();
            return loader.stream()
                         .filter( factory -> factory.getClass().getCanonicalName().equals( factoryName ) )
                         .findFirst() // TODO: Duplication check?
                         .map( p -> p.get().provide( context ) )
                         .orElseThrow( () -> new IllegalStateException(
                                 "Could not find server implementation named '" + factoryName +
                                 "'. Recheck your configuration." ) );

        } else {
            // TODO: Should fail if multiple server is available?
            ServerFactory factory = loader.iterator().next();
            Server server = factory.provide( context );
            logger.info( "Auto selected server implementation: " + server.getClass().getCanonicalName() );
            return server;
        }
    }
}
