package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.Server;
import rip.deadcode.abukuma3.ServerSpec;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.handler.internal.DefaultExceptionHandler;
import rip.deadcode.abukuma3.internal.utils.CheckedUnaryOperator;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.internal.EmptyRouter;
import rip.deadcode.abukuma3.value.Config;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapList;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class ServerSpecImpl implements ServerSpec {

    private Registry registry;
    private Config config;
    private Router router;
    private PersistentList<Parser> parser;
    private PersistentList<Renderer> renderer;
    private PersistentList<Filter> filter;
    private ExceptionHandler exceptionHandler;
    private PersistentList<Module> module;

    public ServerSpecImpl( Config config ) {
        this(
                new RegistryImpl(),
                config,
                new EmptyRouter(),
                createList(),
                createList(),
                createList(),
                new DefaultExceptionHandler(),
                createList()
        );
    }

    private ServerSpecImpl(
            Registry registry,
            Config config,
            Router router,
            List<Parser> parser,
            List<Renderer> renderer,
            List<Filter> filter,
            ExceptionHandler exceptionHandler,
            List<Module> module
    ) {
        this.registry = registry;
        this.config = config;
        this.router = router;
        this.parser = wrapList( parser );
        this.renderer = wrapList( renderer );
        this.filter = wrapList( filter );
        this.exceptionHandler = exceptionHandler;
        this.module = wrapList( module );
    }

    private ServerSpecImpl copy() {
        return new ServerSpecImpl(
                registry,
                config,
                router,
                parser,
                renderer,
                filter,
                exceptionHandler,
                module
        );
    }

    @Override
    public Registry registry() {
        return registry;
    }

    @Override
    public ServerSpec registry( CheckedUnaryOperator<Registry, Exception> registry ) {
        checkNotNull( registry );
        ServerSpecImpl copy = copy();
        copy.registry = uncheck( () -> registry.apply( this.registry ) );
        return copy;
    }

    @Override
    public Config config() {
        return config;
    }

    @Override
    public ServerSpecImpl config( Config config ) {
        checkNotNull( config );
        ServerSpecImpl copy = copy();
        copy.config = config;
        return copy;
    }

    @Override
    public ServerSpec config( CheckedUnaryOperator<Config, Exception> config ) {
        checkNotNull( config );
        ServerSpecImpl copy = copy();
        copy.config = uncheck( () -> config.apply( this.config ) );
        return null;
    }

    @Override
    public Router router() {
        return router;
    }

    @Override
    public ServerSpecImpl router( Router router ) {
        checkNotNull( router );
        ServerSpecImpl copy = copy();
        copy.router = router;
        return copy;
    }

    @Override
    public PersistentList<Parser> parsers() {
        return parser;
    }

    @Override
    public ServerSpecImpl parser( List<Parser> parsers ) {
        checkNotNull( parsers );
        ServerSpecImpl copy = copy();
        copy.parser = wrapList( parsers );
        return copy;
    }

    @Override
    public ServerSpec addParser( Parser parser ) {
        checkNotNull( parser );
        ServerSpecImpl copy = copy();
        copy.parser = copy.parser.addLast( parser );
        return copy;
    }

    @Override
    public PersistentList<Renderer> renderers() {
        return renderer;
    }

    @Override
    public ServerSpecImpl renderer( List<Renderer> renderers ) {
        checkNotNull( renderers );
        ServerSpecImpl copy = copy();
        copy.renderer = wrapList( renderers );
        return copy;
    }

    @Override
    public ServerSpec addRenderer( Renderer renderer ) {
        checkNotNull( renderer );
        ServerSpecImpl copy = copy();
        copy.renderer = copy.renderer.addLast( renderer );
        return copy;
    }

    @Override
    public PersistentList<Filter> filters() {
        return filter;
    }

    @Override
    public ServerSpecImpl filter( List<Filter> filters ) {
        checkNotNull( filters );
        ServerSpecImpl copy = copy();
        copy.filter = wrapList( filters );
        return copy;
    }

    @Override public ServerSpec addFilter( Filter filter ) {
        return null;
    }

    @Override @Nullable
    public ExceptionHandler exceptionHandler() {
        return exceptionHandler;
    }

    @Override
    public ServerSpecImpl exceptionHandler( ExceptionHandler exceptionHandler ) {
        checkNotNull( exceptionHandler );
        ServerSpecImpl copy = copy();
        copy.exceptionHandler = exceptionHandler;
        return copy;
    }

    @Override
    public PersistentList<Module> modules() {
        return module;
    }

    @Override
    public ServerSpecImpl module( List<Module> modules ) {
        checkNotNull( modules );
        ServerSpecImpl copy = copy();
        copy.module = wrapList( modules );
        return copy;
    }

    @Override public ServerSpec addModule( Module module ) {
        checkNotNull( module );
        ServerSpecImpl copy = copy();
        copy.module = copy.module.addLast( module );
        return copy;
    }

    @Override
    public Server createServer() {
        return ServerSpecUtils.build( this );
    }

    @Override public void run() {
        createServer().run();
    }
}
