package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.value.AbuConfig;

import java.util.List;

public final class ExecutionContextImpl implements AbuExecutionContext {

    private final AbuConfig config;
    private final List<AbuParser<?>> parsers;
    private final AbuParser<?> parserChain;
    private final List<AbuRenderer> renderers;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;

    public ExecutionContextImpl(
            AbuConfig config,
            List<AbuParser<?>> parser,
            AbuParser<?> parserChain,
            List<AbuRenderer> renderers,
            AbuRouter router,
            AbuExceptionHandler exceptionHandler
    ) {
        this.config = config;
        this.parsers = parser;
        this.parserChain = parserChain;
        this.renderers = renderers;
        this.router = router;
        this.exceptionHandler = exceptionHandler;
    }

    @Override public AbuConfig getConfig() {
        return config;
    }

    @Override public List<AbuParser<?>> getParsers() {
        return parsers;
    }

    @Override public AbuParser<?> getParserChain() {
        return parserChain;
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
