package rip.deadcode.abukuma3.internal;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.ServerFactory;
import rip.deadcode.abukuma3.ServerSpec;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.filter.Filters;
import rip.deadcode.abukuma3.handler.internal.DefaultExceptionHandler;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.parser.internal.InputStreamParser;
import rip.deadcode.abukuma3.parser.internal.StringParser;
import rip.deadcode.abukuma3.parser.internal.UrlEncodedParser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.internal.CharSequenceRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.renderer.internal.PathRenderer;
import rip.deadcode.abukuma3.utils.internal.DefaultModule;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.reduce;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


public final class ServerSpecUtils {

    private ServerSpecUtils() {
        throw new Error();
    }

    private static final Logger logger = LoggerFactory.getLogger( ServerSpecUtils.class );

    private static final List<Parser<?>> defaultParsers = ImmutableList.of(
            new UrlEncodedParser(),
            new StringParser(),
            new InputStreamParser()
    );

    private static final List<Renderer> defaultRenderers = ImmutableList.of(
            new PathRenderer(),
            new CharSequenceRenderer(),
            new InputStreamRenderer()
    );

    private static final Module defaultModule = new DefaultModule();

    public static Server build( ServerSpec spec ) {

        //noinspection OptionalGetWithoutIsPresent  should have at least one default implementations
        ExecutionContext context = new ExecutionContextImpl(
                spec.registry(),
                checkNotNull( spec.config() ),
                spec.parsers()
                    .concat( defaultParsers )
                    .stream().reduce( Parser::ifFailed ).get(),
                spec.renderers()
                    .concat( defaultRenderers )
                    .stream().reduce( Renderer::ifFailed ).get(),
                spec.filters().stream().reduce( Filter::then ).orElseGet( Filters::noop ),
                checkNotNull( spec.router() ),
                coalesce( spec.exceptionHandler(), DefaultExceptionHandler::new ).get()
        );

        ExecutionContext contextModuleApplied = reduce(
                spec.modules().addLast( defaultModule ),
                context,
                ExecutionContext::applyModule
        );

        ServiceLoader<ServerFactory> loader = ServiceLoader.load( ServerFactory.class );
        Optional<String> requestedFactoryName = contextModuleApplied.config().serverImplementation();

        if ( requestedFactoryName.isPresent() ) {
            String factoryName = requestedFactoryName.get();
            // Should use loader.stream() for JDK9+
            return Streams.stream( loader )
                          .filter( factory -> factory.getClass().getCanonicalName().equals( factoryName ) )
                          .findFirst()
                          .map( f -> f.provide( context ) )
                          .orElseThrow( () -> new IllegalStateException(
                                  "Could not find server implementation named '" + factoryName +
                                  "'. Recheck your configuration." ) );

        } else {
            ServerFactory factory = loader.iterator().next();
            Server server = factory.provide( contextModuleApplied );
            logger.info( "Auto selected server implementation: " + server.getClass().getCanonicalName() );
            return server;
        }
    }
}
