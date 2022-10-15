package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;

import java.util.function.UnaryOperator;


public final class RegistryUtils {

    @SuppressWarnings( "unchecked" )
    public static Registry addParser( Registry registry, Parser parser ) {
        return append( registry, Parser.class, p -> p.ifFailed( parser ) );
    }

    public static Registry addRenderer( Registry registry, Renderer renderer ) {
        return append( registry, Renderer.class, r -> r.ifFailed( renderer ) );
    }

    public static Registry addFilter( Registry registry, Filter filter ) {
        return append( registry, Filter.class, f -> f.then( filter ) );
    }

    public static <T> Registry append( Registry registry, Class<T> cls, UnaryOperator<T> appender ) {
        T current = registry.get( cls );
        return registry.setSingleton( cls, appender.apply( current ) );
    }
}
