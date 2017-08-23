package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.route.matcher.AntMatcher;
import rip.deadcode.abukuma3.route.matcher.MatchResult;
import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.Service;

public final class AntRouter implements Router {

    private final AntMatcher matcher = new AntMatcher();
    private final String pattern;
    private Service service;

    public AntRouter(String pattern, Service service) {
        this.pattern = pattern;
        this.service = service;
    }

    @Override
    public RoutingResult proceed(Context context) {
        MatchResult matched = matcher.matches(pattern, context.getRequest().path());

        return matched.isMatched()
               ? new RoutingResultImpl(service.run(context.pathParam(matched.getParameters())))
               : RoutingResult.notMatched();
    }

}
