package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.Service;

import static com.google.common.base.Preconditions.checkNotNull;

public final class AlwaysMatchRouter implements Router {

    private final Service service;

    public AlwaysMatchRouter(Service service) {
        this.service = service;
    }

    @Override
    public RoutingResult proceed(Context context) {
        Context result = service.run(context);
        checkNotNull(result, "Service returned null context.");
        return new RoutingResultImpl(result);
    }

}
