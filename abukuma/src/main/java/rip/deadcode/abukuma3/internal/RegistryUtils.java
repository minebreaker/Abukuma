package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;

import java.util.function.UnaryOperator;


public final class RegistryUtils {

    @SuppressWarnings( "unchecked" )
    public static Registry addParser( Registry registry, AbuParser<?> parser ) {
        return append( registry, AbuParser.class, p -> p.ifFailed( parser ) );
    }

    public static Registry addRenderer( Registry registry, AbuRenderer renderer ) {
        return append( registry, AbuRenderer.class, r -> r.ifFailed( renderer ) );
    }

    public static Registry addFilter( Registry registry, AbuFilter filter ) {
        return append( registry, AbuFilter.class, f -> f.then( filter ) );
    }

    public static <T> Registry append( Registry registry, Class<T> cls, UnaryOperator<T> appender ) {
        T current = registry.get( cls );
        return registry.setSingleton( cls, appender.apply( current ) );
    }
}
