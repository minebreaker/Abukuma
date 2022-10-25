package rip.deadcode.abukuma3.internal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Module;
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
import java.util.ServiceLoader;

import static java.util.stream.Collectors.joining;
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

        var registryPluginsApplied = setupConfigs( spec );
        var pluginModules = getModules();
        var executionContext = createExecutionContext( registryPluginsApplied, spec, pluginModules );

        return createServer( executionContext );
    }

    private static PersistentList<Module> getModules() {
        var loader = ServiceLoader.load( Module.class );
        return loader.stream()
                     .map( p -> p.get() )
                     .collect( toPersistentList() );
    }

    private static Registry setupConfigs( ServerSpec spec ) {

        var serverConfig = spec.config();
        return spec.registry()
                   // Add Config object
                   .setSingleton( Config.class, serverConfig )
                   .setSingleton( com.typesafe.config.Config.class, serverConfig.original() );
    }

    private static ExecutionContext createExecutionContext(
            Registry registry,
            ServerSpec spec,
            List<Module> pluginModules ) {
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

        return reduceSequentially(
                spec.modules()
                    .concat( pluginModules )
                    .addLast( defaultModule ),
                context,
                ExecutionContext::applyModule
        );
    }

    private static Server createServer( ExecutionContext context ) {

        var loader = ServiceLoader.load( ServerFactory.class );
        var factories = loader.stream().collect( toPersistentList() );

        if ( factories.isEmpty() ) {
            throw new RuntimeException( "No server implementation found." );
        }

        if ( factories.size() != 1 ) {
            var names = factories.stream()
                                 .map( s -> s.getClass().getCanonicalName() )
                                 .collect( joining( System.lineSeparator() ) );
            throw new RuntimeException( "Multiple server implementations found. Check your dependencies.\n" + names );
        }

        Server server = factories.first().get().provide( context );
        logger.info( "Using server implementation: " + server.getClass().getCanonicalName() );
        return server;
    }
}
