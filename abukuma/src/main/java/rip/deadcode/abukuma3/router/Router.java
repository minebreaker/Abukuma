package rip.deadcode.abukuma3.router;

import javax.annotation.Nullable;


@FunctionalInterface
public interface Router {

    @Nullable
    public RoutingResult route( RoutingContext context );

    public default Router ifNotMatch( Router downstream ) {
        return context -> {
            RoutingResult result = this.route( context );
            return result != null ? result : downstream.route( context );
        };
    }
}
