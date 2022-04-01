package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.collection.PersistentMap;

import javax.annotation.Nullable;
import java.util.Map;


@FunctionalInterface
public interface RouteMatcher {

    /**
     * @param context RoutingContext to check if this route matches to.
     * @return Returns {@link Map} representing path parameters if the route matches.
     *         Returns {@code null} if the route does not match.
     */
    @Nullable
    public PersistentMap<String, String> matches( RoutingContext context );

    public default RouteMatcher and( RouteMatcher another ) {
        return context -> {
            PersistentMap<String, String> resultL = this.matches( context );
            PersistentMap<String, String> resultR = another.matches( context );
            if ( resultL == null || resultR == null ) {
                return null;
            }
            return resultL.merge( resultR );
        };
    }

    public default RouteMatcher ifNotMatch( RouteMatcher downstream ) {
        return context -> {
            PersistentMap<String, String> result = this.matches( context );
            return result != null
                   ? result
                   : downstream.matches( context );
        };
    }
}
