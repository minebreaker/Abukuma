package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.config.AbuConfig;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;

import java.util.List;
import java.util.Map;

public final class ExecutionContextImpl implements ExecutionContext {

    private final AbuConfig config;
    private final Map<Class<?>, AbuParser<?>> parsers;
    private final List<AbuRenderer> renderers;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;

    public ExecutionContextImpl(
            AbuConfig config,
            Map<Class<?>, AbuParser<?>> parser,
            List<AbuRenderer> renderers,
            AbuRouter router,
            AbuExceptionHandler exceptionHandler
    ) {
        this.config = config;
        this.parsers = parser;
        this.renderers = renderers;
        this.router = router;
        this.exceptionHandler = exceptionHandler;
    }

    @Override public AbuConfig getConfig() {
        return config;
    }

    @Override public Map<Class<?>, AbuParser<?>> getParsers() {
        return parsers;
    }

    @Override public List<AbuRenderer> getRenderers() {
        return renderers;
    }

    @Override public AbuRouter getRouter() {
        return router;
    }

    @Override public AbuExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }
}
