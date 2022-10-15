package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.internal.utils.CheckedUnaryOperator;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.value.Config;

import java.util.List;


public interface ServerSpec {

    public Registry registry();

    public ServerSpec registry( CheckedUnaryOperator<Registry, Exception> registry );

    public Config config();

    public ServerSpec config( Config config );

    public ServerSpec config( CheckedUnaryOperator<Config, Exception> config );

    public Router router();

    public ServerSpec router( Router router );

    public PersistentList<Parser> parsers();

    public ServerSpec parser( List<Parser> parsers );

    public ServerSpec addParser( Parser parser );

    public PersistentList<Renderer> renderers();

    public ServerSpec renderer( List<Renderer> renderers );

    public ServerSpec addRenderer( Renderer renderer );

    public PersistentList<Filter> filters();

    public ServerSpec filter( List<Filter> filters );

    public ServerSpec addFilter( Filter filter );

    public ExceptionHandler exceptionHandler();

    public ServerSpec exceptionHandler( ExceptionHandler exceptionHandler );

    public PersistentList<Module> modules();

    public ServerSpec module( List<Module> modules );

    public ServerSpec addModule( Module module );

    public Server createServer();

    /**
     * Convenience method of `createServer().run()`
     */
    public void run();
}
