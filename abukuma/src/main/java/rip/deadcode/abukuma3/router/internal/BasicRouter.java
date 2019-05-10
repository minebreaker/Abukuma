package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.Route;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;
import java.util.Map;


public final class BasicRouter implements AbuRouter {

    private final Route route;

    public BasicRouter( Route route ) {
        this.route = route;
    }

    @Nullable
    @Override
    public RoutingResult route( RoutingContext context ) {
        Map<String, String> match = route.matcher().matches( context );
        return match == null ? null : new RoutingResultImpl( route.handler(), match );
    }
}
