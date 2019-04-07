package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.value.AbuConfig;


public interface AbuExecutionContext extends Registry {

    public AbuConfig config();

    public AbuParser<?> parser();

    public AbuRenderer renderer();

    public AbuFilter filter();

    public AbuRouter router();

    public AbuExceptionHandler exceptionHandler();

    public AbuExecutionContext applyModule(Module module);
}
