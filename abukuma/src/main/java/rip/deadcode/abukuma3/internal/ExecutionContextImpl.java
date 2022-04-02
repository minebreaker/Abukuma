package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.utils.url.UrlParser;
import rip.deadcode.abukuma3.utils.url.UrlPathParser;
import rip.deadcode.abukuma3.utils.url.UrlQueryParser;
import rip.deadcode.abukuma3.utils.url.internal.BasicUrlPathParser;
import rip.deadcode.abukuma3.utils.url.internal.JavaUrlParser;
import rip.deadcode.abukuma3.utils.url.internal.JavaUrlQueryParser;
import rip.deadcode.abukuma3.value.Config;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


public final class ExecutionContextImpl implements ExecutionContext {

    private final Registry registry;

    private ExecutionContextImpl( Registry delegate ) {
        this.registry = delegate;
    }

    public ExecutionContextImpl(
            Registry registry,
            Config config,
            Parser<?> parserChain,
            Renderer renderer,
            Filter filterChain,
            Router router,
            ExceptionHandler exceptionHandler
    ) {
        this.registry = registry
                .setSingleton( Config.class, config )
                .setSingleton( Parser.class, parserChain )
                .setSingleton( Renderer.class, renderer )
                .setSingleton( Filter.class, filterChain )
                .setSingleton( Router.class, router )
                .setSingleton( ExceptionHandler.class, exceptionHandler )
                .setSingleton( UrlParser.class, JavaUrlParser::parseStatic )
                .setSingleton( UrlPathParser.class, BasicUrlPathParser::parseStatic )
                .setSingleton( UrlQueryParser.class, JavaUrlQueryParser::parseStatic );
    }

    @Override public Config config() {
        return registry.get( Config.class );
    }

    @Override public Parser<?> parser() {
        return registry.get( Parser.class );
    }

    @Override public Renderer renderer() {
        return registry.get( Renderer.class );
    }

    @Override public Filter filter() {
        return registry.get( Filter.class );
    }

    @Override public Router router() {
        return registry.get( Router.class );
    }

    @Override public ExceptionHandler exceptionHandler() {
        return registry.get( ExceptionHandler.class );
    }

    @Override
    public <T> T get( Class<T> cls ) {
        return registry.get( cls );
    }

    @Override
    public <T> T get( Class<T> cls, String name ) {
        return registry.get( cls, name );
    }

    @Override public <T> Optional<T> mayGet( Class<T> cls ) {
        return registry.mayGet( cls );
    }

    @Override public <T> Optional<T> mayGet( Class<T> cls, String name ) {
        return registry.mayGet( cls, name );
    }

    @Override
    public <T> ExecutionContext setSingleton( Class<T> cls, T instance ) {
        return new ExecutionContextImpl( registry.setSingleton( cls, instance ) );
    }

    @Override
    public <T> ExecutionContext set( Class<T> cls, Supplier<? extends T> supplier ) {
        return new ExecutionContextImpl( registry.set( cls, supplier ) );
    }

    @Override public <T> Registry set( Class<T> cls, Function<Registry, ? extends T> generator ) {
        return new ExecutionContextImpl( registry.set( cls, generator ) );
    }

    @Override
    public <T> ExecutionContext set( Class<T> cls, String name, Supplier<? extends T> supplier ) {
        return new ExecutionContextImpl( registry.set( cls, name, supplier ) );
    }

    @Override public <T> Registry set( Class<T> cls, String name, Function<Registry, ? extends T> generator ) {
        return new ExecutionContextImpl( registry.set( cls, name, generator ) );
    }

    @Override public ExecutionContext applyModule( Module module ) {
        return new ExecutionContextImpl( module.apply( this ) );
    }
}
