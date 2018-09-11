package rip.deadcode.abukuma3;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.value.AbuConfig;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.internal.AbuServerImpl;
import rip.deadcode.abukuma3.internal.DefaultExceptionHandler;
import rip.deadcode.abukuma3.internal.ExecutionContextImpl;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.parser.InputStreamParser;
import rip.deadcode.abukuma3.parser.StringParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.renderer.internal.InputStreamRenderer;
import rip.deadcode.abukuma3.renderer.internal.StringRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Abukuma {

    public static AbuServerBuilder config( AbuConfig config ) {
        return new AbuServerBuilder().config( config );
    }

    @NotThreadSafe
    public static final class AbuServerBuilder {

        private AbuConfig config;
        private AbuRouter router;
        private Map<Class<?>, AbuParser<?>> parsers;
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

        public AbuServerBuilder parser( Map<Class<?>, AbuParser<?>> parsers ) {
            this.parsers = ImmutableMap.copyOf( parsers );
            return this;
        }

        public <T> AbuServerBuilder addParser( Class<T> cls, AbuParser<? extends T> parser ) {
            this.parsers = ImmutableMap.<Class<?>, AbuParser<?>>builder()
                    .putAll( this.parsers )
                    .put( cls, parser )
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
//            checkNotNull( parsers );
            if ( parsers == null ) {
                parsers = ImmutableMap.of(
                        String.class, new StringParser(),
                        InputStream.class, new InputStreamParser()
                );
            } else {
                parsers = ImmutableMap.<Class<?>, AbuParser<?>>builder()
                        .put( String.class, new StringParser() )
                        .put( InputStream.class, new InputStreamParser() )
                        .putAll( parsers )
                        .build();
            }
//            checkNotNull( renderers );
            if ( renderers == null ) {
                this.renderers = ImmutableList.of( new StringRenderer(), new InputStreamRenderer() );
            } else {
                addRenderers( new StringRenderer(), new InputStreamRenderer() );
            }
            if ( exceptionHandler == null ) exceptionHandler = new DefaultExceptionHandler();
        }

        public AbuServer build() {
            check();
            return new AbuServerImpl( new ExecutionContextImpl(
                    config,
                    parsers,
                    renderers,
                    router,
                    exceptionHandler
            ) );
        }
    }

}
