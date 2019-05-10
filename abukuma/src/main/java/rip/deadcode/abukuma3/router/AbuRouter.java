package rip.deadcode.abukuma3.router;

import javax.annotation.Nullable;


@FunctionalInterface
public interface AbuRouter {
    @Nullable
    public RoutingResult route( RoutingContext context );

    public default AbuRouter ifNotMatch( AbuRouter downstream ) {
        return context -> {
            RoutingResult result = this.route( context );
            return result != null ? result : downstream.route( context );
        };
    }
}
