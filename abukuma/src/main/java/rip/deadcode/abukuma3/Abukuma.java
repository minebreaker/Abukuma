package rip.deadcode.abukuma3;

import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.internal.AbuServerImpl;
import rip.deadcode.abukuma3.internal.DefaultExceptionHandler;
import rip.deadcode.abukuma3.internal.ExecutionContextImpl;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.parser.internal.InputStreamParser;
import rip.deadcode.abukuma3.parser.internal.StringParser;
import rip.deadcode.abukuma3.parser.internal.UrlEncodedParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.renderer.internal.CharSequenceRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.value.AbuConfig;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Abukuma {

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

        private AbuConfig config;
        private AbuRouter router;
        private List<AbuParser<?>> parsers;
        private List<AbuRenderer> renderers;
        private AbuExceptionHandler exceptionHandler;

        private AbuServerBuilder() { }

        public AbuServerBuilder config( AbuConfig config ) {
            this.config = config;
            return this;
        }

        public AbuServerBuilder router( AbuRouter router ) {
            this.router = router;
            return this;
        }

        public AbuServerBuilder addParser( AbuParser<?> parser ) {
            this.parsers = ImmutableList.<AbuParser<?>>builder()
                    .add( parser )
                    .addAll( parsers )
                    .build();
            return this;
        }

        public AbuServerBuilder renderer( List<AbuRenderer> renderers ) {
            this.renderers = ImmutableList.copyOf( renderers );
            return this;
        }

        public AbuServerBuilder addRenderer( AbuRenderer renderer ) {
            this.renderers = ImmutableList.<AbuRenderer>builder()
                    .addAll( renderers )
                    .add( renderer )
                    .build();
            return this;
        }

        public AbuServerBuilder addRenderers( AbuRenderer... renderer ) {
            this.renderers = ImmutableList.<AbuRenderer>builder()
                    .addAll( renderers )
                    .add( renderer )
                    .build();
            return this;
        }

        public AbuServerBuilder exceptionHandler( AbuExceptionHandler exceptionHandler ) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        private void check() {
            checkNotNull( config );
            checkNotNull( router );
            if ( parsers == null ) {
                parsers = defaultParsers;
            } else {
                parsers = ImmutableList.<AbuParser<?>>builder()
                        .addAll( parsers )
                        .addAll( defaultParsers )
                        .build();
            }
            if ( renderers == null ) {
                this.renderers = ImmutableList.of( new CharSequenceRenderer(), new InputStreamRenderer() );
            } else {
                addRenderers( new CharSequenceRenderer(), new InputStreamRenderer() );
            }
            if ( exceptionHandler == null ) exceptionHandler = new DefaultExceptionHandler();
        }

        public AbuServer build() {
            check();
            //noinspection OptionalGetWithoutIsPresent  should have at least one default implementations
            return new AbuServerImpl( new ExecutionContextImpl(
                    config,
                    parsers,
                    parsers.stream().reduce( AbuParser::ifFailed ).get(),
                    renderers,
                    router,
                    exceptionHandler
            ) );
        }
    }

}
