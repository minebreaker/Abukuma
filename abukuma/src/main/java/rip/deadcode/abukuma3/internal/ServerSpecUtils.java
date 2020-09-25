package rip.deadcode.abukuma3.internal;


import com.google.common.collect.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.ServerFactory;
import rip.deadcode.abukuma3.ServerSpec;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.filter.Filters;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;

import java.util.Optional;
import java.util.ServiceLoader;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.reduce;


public final class ServerSpecUtils {

    private ServerSpecUtils() {
        throw new Error();
    }

    private static final Logger logger = LoggerFactory.getLogger( ServerSpecUtils.class );

    public static Server build( ServerSpec spec ) {

        //noinspection OptionalGetWithoutIsPresent  should have at least one default implementations
        ExecutionContext context = new ExecutionContextImpl(
                spec.registry(),
                checkNotNull( spec.config() ),
                spec.parsers().stream().reduce( Parser::ifFailed ).get(),
                spec.renderers().stream().reduce( Renderer::ifFailed ).get(),
                spec.filters().stream().reduce( Filter::then ).orElseGet( Filters::noop ),
                checkNotNull( spec.router() ),
                checkNotNull( spec.exceptionHandler() )
        );

        ExecutionContext c = reduce(
                spec.modules(),
                context,
                ExecutionContext::applyModule
        );

        ServiceLoader<ServerFactory> loader = ServiceLoader.load( ServerFactory.class );
        Optional<String> requestedFactoryName = context.config().serverImplementation();

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
            Server server = factory.provide( context );
            logger.info( "Auto selected server implementation: " + server.getClass().getCanonicalName() );
            return server;
        }
    }
}
