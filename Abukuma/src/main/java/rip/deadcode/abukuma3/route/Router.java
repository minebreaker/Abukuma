package rip.deadcode.abukuma3.route;

import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.Service;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public interface Router {

    public RoutingResult proceed(Context context);

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final List<Router> routes = new ArrayList<>();

        public Builder route(Router router) {
            checkNotNull(router);
            routes.add(router);
            return this;
        }

        public Builder context(String context, Router... children) {
            routes.add(new ContextualRouter(
                    context,
                    new BindingRouter(ImmutableList.copyOf(children))
            ));
            return this;
        }

        public Builder notFound(Service service) {
            checkNotNull(service);
            routes.add(new AlwaysMatchRouter(service));
            return this;
        }

        public Router build() {
            return new BindingRouter(ImmutableList.copyOf(routes));
        }

    }

}
