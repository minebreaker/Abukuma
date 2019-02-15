package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.value.AbuConfig;

import java.util.List;


public interface AbuExecutionContext {

    public AbuConfig config();

    public List<AbuParser<?>> parsers();

    public AbuParser<?> parserChain();

    public List<AbuRenderer> renderers();

    public List<AbuFilter> filters();

    public AbuFilter filterChain();

    public AbuRouter router();

    public AbuExceptionHandler exceptionHandler();
}
