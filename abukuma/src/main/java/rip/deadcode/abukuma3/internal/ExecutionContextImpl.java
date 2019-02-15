package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.filter.AbuFilter;
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
    private final List<AbuFilter> filters;
    private final AbuFilter filterChain;
    private final AbuRouter router;
    private final AbuExceptionHandler exceptionHandler;

    public ExecutionContextImpl(
            AbuConfig config,
            List<AbuParser<?>> parser,
            AbuParser<?> parserChain,
            List<AbuRenderer> renderers,
            List<AbuFilter> filters,
            AbuFilter filterChain,
            AbuRouter router,
            AbuExceptionHandler exceptionHandler
    ) {
        this.config = config;
        this.parsers = parser;
        this.parserChain = parserChain;
        this.renderers = renderers;
        this.filters = filters;
        this.filterChain = filterChain;
        this.router = router;
        this.exceptionHandler = exceptionHandler;
    }

    @Override public AbuConfig config() {
        return config;
    }

    @Override public List<AbuParser<?>> parsers() {
        return parsers;
    }

    @Override public AbuParser<?> parserChain() {
        return parserChain;
    }

    @Override public List<AbuRenderer> renderers() {
        return renderers;
    }

    @Override public List<AbuFilter> filters() {
        return filters;
    }

    @Override public AbuFilter filterChain() {
        return filterChain;
    }

    @Override public AbuRouter router() {
        return router;
    }

    @Override public AbuExceptionHandler exceptionHandler() {
        return exceptionHandler;
    }
}
