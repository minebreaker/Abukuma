package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.handler.ExceptionHandler;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.value.Config;


public interface ExecutionContext extends Registry {

    public Config config();

    public Parser<?> parser();

    public Renderer renderer();

    public Filter filter();

    public Router router();

    public ExceptionHandler exceptionHandler();

    public ExecutionContext applyModule( Module module);
}
