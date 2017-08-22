package rip.deadcode.abukuma3.route;

import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.service.Context;

import java.util.ArrayList;
import java.util.List;

public interface Router {

    public RoutingResult proceed(Context context);

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final List<Router> routes = new ArrayList<>();

        public Builder route(Router router) {
            routes.add(router);
            return this;
        }

        public Router build() {
            return new BindingRouter(ImmutableList.copyOf(routes));
        }

    }

}
