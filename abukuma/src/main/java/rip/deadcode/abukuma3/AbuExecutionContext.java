package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.value.AbuConfig;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;

import java.util.List;
import java.util.Map;

public interface AbuExecutionContext {

    public AbuConfig getConfig();

    public Map<Class<?>, AbuParser<?>> getParsers();

    public List<AbuRenderer> getRenderers();

    public AbuRouter getRouter();

    public AbuExceptionHandler getExceptionHandler();
}
