package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.Service;

public final class SimpleStuipdRouter implements Router {

    private final String pattern;
    private final Service service;

    public SimpleStuipdRouter(String pattern, Service service) {
        this.pattern = pattern;
        this.service = service;
    }

    @Override
    public RoutingResult proceed(Context context) {
        if (context.getContextualPath().equals(pattern)) {
            return new RoutingResultImpl(service.run(context));
        } else {
            return RoutingResult.notMatched();
        }
    }

}
