package rip.deadcode.abukuma3;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.handler.internal.DefaultExceptionHandler;
import rip.deadcode.abukuma3.internal.Information;
import rip.deadcode.abukuma3.internal.RegistryImpl;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.parser.internal.InputStreamParser;
import rip.deadcode.abukuma3.parser.internal.StringParser;
import rip.deadcode.abukuma3.parser.internal.UrlEncodedParser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.renderer.internal.CharSequenceRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.renderer.internal.PathRenderer;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RouterSpec;
import rip.deadcode.abukuma3.utils.internal.DefaultModule;
import rip.deadcode.abukuma3.value.Config;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkNotNull;


public final class Abukuma {

    private static final Logger logger = LoggerFactory.getLogger( Abukuma.class );

    private Abukuma() {
        throw new Error();
    }

//    public static ServerSpec create() {
//        return new ServerSpecImpl();
//    }

    public static AbuServerBuilder config( Config config ) {
        return new AbuServerBuilder().config( config );
    }

    @NotThreadSafe
    public static final class AbuServerBuilder {

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

        private Registry registry = new RegistryImpl();
        private Config config;
        private Router router;
        private List<Parser<?>> parsers = ImmutableList.of();
        private List<Renderer> renderers = ImmutableList.of();
        private List<Filter> filters = ImmutableList.of();
        private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();
        private List<Module> modules = ImmutableList.of( new DefaultModule() );

        private AbuServerBuilder() {
            logger.info( Information.INFO_STRING );
        }

        public AbuServerBuilder config( Config config ) {
            this.config = config;
            return this;
        }

        public AbuServerBuilder router( RouterSpec router ) {
            // TODO RouterSpec -> RouterSpec
            this.router = router.createRouter();
            return this;
        }

        public AbuServerBuilder addParser( Parser<?> parser ) {
            this.parsers = ImmutableList.<Parser<?>>builder()
                    .add( parser )
                    .addAll( parsers )
                    .build();
            return this;
        }

        public AbuServerBuilder addParsers( Parser<?>... parsers ) {
            this.parsers = ImmutableList.<Parser<?>>builder()
                    .add( parsers )
                    .addAll( this.parsers )
                    .build();
            return this;
        }


        public AbuServerBuilder addParsers( List<Parser<?>> parsers ) {
            this.parsers = ImmutableList.<Parser<?>>builder()
                    .addAll( parsers )
                    .addAll( this.parsers )
                    .build();
            return this;
        }

        public AbuServerBuilder addRenderer( Renderer renderer ) {
            this.renderers = ImmutableList.<Renderer>builder()
                    .addAll( renderers )
                    .add( renderer )
                    .build();
            return this;
        }

        public AbuServerBuilder addRenderers( Renderer... renderers ) {
            this.renderers = ImmutableList.<Renderer>builder()
                    .addAll( this.renderers )
                    .add( renderers )
                    .build();
            return this;
        }


        public AbuServerBuilder addRenderers( List<Renderer> renderers ) {
            this.renderers = ImmutableList.<Renderer>builder()
                    .addAll( this.renderers )
                    .addAll( renderers )
                    .build();
            return this;
        }

        public AbuServerBuilder addFilter( Filter filter ) {
            this.filters = ImmutableList.<Filter>builder()
                    .addAll( filters )
                    .add( filter )
                    .build();
            return this;
        }

        public AbuServerBuilder addFilter( Filter... filters ) {
            this.filters = ImmutableList.<Filter>builder()
                    .addAll( this.filters )
                    .add( filters )
                    .build();
            return this;
        }

        public AbuServerBuilder addFilter( List<Filter> filters ) {
            this.filters = ImmutableList.<Filter>builder()
                    .addAll( this.filters )
                    .addAll( filters )
                    .build();
            return this;
        }

        public AbuServerBuilder exceptionHandler( ExceptionHandler exceptionHandler ) {
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

            parsers = ImmutableList.<Parser<?>>builder()
                    .addAll( parsers )
                    .addAll( defaultParsers )
                    .build();
            addRenderers( defaultRenderers );
        }

        public Server build() {
            throw new Error();
        }

        private static Server createServer( ExecutionContext context ) {
            throw new Error();
        }
    }
}
