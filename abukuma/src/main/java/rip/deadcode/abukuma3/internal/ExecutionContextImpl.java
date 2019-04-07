package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.handler.AbuExceptionHandler;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.value.AbuConfig;

import java.util.function.Supplier;


public final class ExecutionContextImpl implements AbuExecutionContext {

    private final Registry registry;

    private ExecutionContextImpl( Registry delegate ) {
        this.registry = delegate;
    }

    public ExecutionContextImpl(
            AbuConfig config,
            AbuParser<?> parserChain,
            AbuRenderer renderer,
            AbuFilter filterChain,
            AbuRouter router,
            AbuExceptionHandler exceptionHandler
    ) {
        this.registry = new RegistryImpl()
                .setSingleton( AbuConfig.class, config )
                .setSingleton( AbuParser.class, parserChain )
                .setSingleton( AbuRenderer.class, renderer )
                .setSingleton( AbuFilter.class, filterChain )
                .setSingleton( AbuRouter.class, router )
                .setSingleton( AbuExceptionHandler.class, exceptionHandler );
    }

    @Override public AbuConfig config() {
        return registry.get( AbuConfig.class );
    }

    @Override public AbuParser<?> parser() {
        return registry.get( AbuParser.class );
    }

    @Override public AbuRenderer renderer() {
        return registry.get( AbuRenderer.class );
    }

    @Override public AbuFilter filter() {
        return registry.get( AbuFilter.class );
    }

    @Override public AbuRouter router() {
        return registry.get( AbuRouter.class );
    }

    @Override public AbuExceptionHandler exceptionHandler() {
        return registry.get( AbuExceptionHandler.class );
    }

    @Override
    public <T> T get( Class<T> cls ) {
        return registry.get( cls );
    }

    @Override
    public <T> T get( Class<T> cls, String name ) {
        return registry.get( cls, name );
    }

    @Override
    public <T> AbuExecutionContext setSingleton( Class<T> cls, T instance ) {
        return new ExecutionContextImpl( registry.setSingleton( cls, instance ) );
    }

    @Override
    public <T> AbuExecutionContext set( Class<T> cls, Supplier<? extends T> supplier ) {
        return new ExecutionContextImpl( registry.set( cls, supplier ) );
    }

    @Override
    public <T> AbuExecutionContext set( Class<T> cls, String name, Supplier<? extends T> supplier ) {
        return new ExecutionContextImpl( registry.set( cls, name, supplier ) );
    }
}
