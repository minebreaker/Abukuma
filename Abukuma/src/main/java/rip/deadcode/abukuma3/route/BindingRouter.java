package rip.deadcode.abukuma3.route;

import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.service.Context;

import java.util.List;

public final class BindingRouter implements Router {

    private final List<Router> routes;

    BindingRouter(List<Router> routers) {
        this.routes = ImmutableList.copyOf(routers);
    }

    @Override
    public RoutingResult proceed(Context context) {
        for (Router route : routes) {
            RoutingResult result = route.proceed(context);
            if (result.routeMatched()) {
                return result;
            }
        }
        return RoutingResult.notMatched();
    }

}
