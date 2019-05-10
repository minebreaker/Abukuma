package rip.deadcode.abukuma3;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.filter.AbuFilters;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.internal.DefaultExceptionHandler;
import rip.deadcode.abukuma3.internal.ExecutionContextImpl;
import rip.deadcode.abukuma3.internal.Information;
import rip.deadcode.abukuma3.internal.RegistryImpl;
import rip.deadcode.abukuma3.internal.utils.MoreCollections;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.parser.internal.InputStreamParser;
import rip.deadcode.abukuma3.parser.internal.StringParser;
import rip.deadcode.abukuma3.parser.internal.UrlEncodedParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.internal.CharSequenceRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.RouterSpec;
import rip.deadcode.abukuma3.value.AbuConfig;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkNotNull;


public final class Abukuma {

    private static final Logger logger = LoggerFactory.getLogger( Abukuma.class );

    private Abukuma() {
        throw new AssertionError();
    }

    public static AbuServerBuilder config( AbuConfig config ) {
        return new AbuServerBuilder().config( config );
    }

    @NotThreadSafe
    public static final class AbuServerBuilder {

        private static final List<AbuParser<?>> defaultParsers = ImmutableList.of(
                new UrlEncodedParser(),
                new StringParser(),
                new InputStreamParser()
        );

        private Registry registry = new RegistryImpl();
        private AbuConfig config;
        private AbuRouter router;
        private List<AbuParser<?>> parsers = ImmutableList.of();
        private List<AbuRenderer> renderers = ImmutableList.of();
        private List<AbuFilter> filters = ImmutableList.of();
        private AbuExceptionHandler exceptionHandler;
        private List<Module> modules = ImmutableList.of();

        private AbuServerBuilder() {
            logger.info( Information.INFO_STRING );
        }

        public AbuServerBuilder config( AbuConfig config ) {
            this.config = config;
            return this;
        }

        public AbuServerBuilder router( RouterSpec router ) {
            // TODO RouterSpec -> RouterSpec
            this.router = router.createRouter();
            return this;
        }

        public AbuServerBuilder addParser( AbuParser<?> parser ) {
            this.parsers = ImmutableList.<AbuParser<?>>builder()
                    .add( parser )
                    .addAll( parsers )
                    .build();
            return this;
        }

        public AbuServerBuilder addParsers( AbuParser<?>... parsers ) {
            this.parsers = ImmutableList.<AbuParser<?>>builder()
                    .add( parsers )
                    .addAll( this.parsers )
                    .build();
            return this;
        }


        public AbuServerBuilder addParsers( List<AbuParser<?>> parsers ) {
            this.parsers = ImmutableList.<AbuParser<?>>builder()
                    .addAll( parsers )
                    .addAll( this.parsers )
                    .build();
            return this;
        }

        public AbuServerBuilder addRenderer( AbuRenderer renderer ) {
            this.renderers = ImmutableList.<AbuRenderer>builder()
                    .addAll( renderers )
                    .add( renderer )
                    .build();
            return this;
        }

        public AbuServerBuilder addRenderers( AbuRenderer... renderers ) {
            this.renderers = ImmutableList.<AbuRenderer>builder()
                    .addAll( this.renderers )
                    .add( renderers )
                    .build();
            return this;
        }


        public AbuServerBuilder addRenderers( List<AbuRenderer> renderers ) {
            this.renderers = ImmutableList.<AbuRenderer>builder()
                    .addAll( this.renderers )
                    .addAll( renderers )
                    .build();
            return this;
        }

        public AbuServerBuilder addFilter( AbuFilter filter ) {
            this.filters = ImmutableList.<AbuFilter>builder()
                    .addAll( filters )
                    .add( filter )
                    .build();
            return this;
        }

        public AbuServerBuilder addFilter( AbuFilter... filters ) {
            this.filters = ImmutableList.<AbuFilter>builder()
                    .addAll( this.filters )
                    .add( filters )
                    .build();
            return this;
        }

        public AbuServerBuilder addFilter( List<AbuFilter> filters ) {
            this.filters = ImmutableList.<AbuFilter>builder()
                    .addAll( this.filters )
                    .addAll( filters )
                    .build();
            return this;
        }

        public AbuServerBuilder exceptionHandler( AbuExceptionHandler exceptionHandler ) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        public AbuServerBuilder registry( UnaryOperator<Registry> registry ) {
            this.registry = registry.apply( this.registry );
            return this;
        }

        public AbuServerBuilder addModule( Module module ) {
            this.modules = ImmutableList.<Module>builder()
                    .addAll( this.modules )
                    .add( module )
                    .build();
            return this;
        }

        public AbuServerBuilder addModules( Module... module ) {
            this.modules = ImmutableList.<Module>builder()
                    .addAll( this.modules )
                    .add( module )
                    .build();
            return this;
        }

        private void check() {
            checkNotNull( config );
            checkNotNull( router );
            checkNotNull( registry );

            if ( parsers == null ) {
                parsers = defaultParsers;
            } else {
                parsers = ImmutableList.<AbuParser<?>>builder()
                        .addAll( parsers )
                        .addAll( defaultParsers )
                        .build();
            }
            if ( renderers == null ) {
                renderers = ImmutableList.of( new CharSequenceRenderer(), new InputStreamRenderer() );
            } else {
                addRenderers( new CharSequenceRenderer(), new InputStreamRenderer() );
            }
            if ( exceptionHandler == null ) exceptionHandler = new DefaultExceptionHandler();
        }

        public AbuServer build() {
            check();
            //noinspection OptionalGetWithoutIsPresent  should have at least one default implementations
            AbuExecutionContext context = new ExecutionContextImpl(
                    registry,
                    config,
                    parsers.stream().reduce( AbuParser::ifFailed ).get(),
                    renderers.stream().reduce( ( r, then ) -> r.ifFailed( then ) ).get(),
                    filters.stream().reduce( AbuFilter::then ).orElseGet( AbuFilters::noop ),
                    router,
                    exceptionHandler
            );

            AbuExecutionContext c = MoreCollections.reduce(
                    this.modules,
                    context,
                    ( acc, m ) -> acc.applyModule( m )
            );

            return createServer( c );
        }

        private static AbuServer createServer( AbuExecutionContext context ) {
            // Should use stream() for JDK9+
            ServiceLoader<ServerFactory> loader = ServiceLoader.load( ServerFactory.class );
            ServerFactory factory = loader.iterator().next();  // TODO specify from configuration
            AbuServer server = factory.provide( context );
            logger.info( "Server implementation: " + server.getClass().getCanonicalName() );
            return server;
        }
    }
}
