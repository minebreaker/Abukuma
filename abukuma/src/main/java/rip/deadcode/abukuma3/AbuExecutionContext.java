package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.value.AbuConfig;

import java.util.List;

public interface AbuExecutionContext {

    public AbuConfig getConfig();

    public List<AbuParser<?>> getParsers();

    public AbuParser<?> getParserChain();

    public List<AbuRenderer> getRenderers();

    public AbuRouter getRouter();

    public AbuExceptionHandler getExceptionHandler();
}
