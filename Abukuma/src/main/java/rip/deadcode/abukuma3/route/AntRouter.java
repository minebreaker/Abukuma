package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.route.matcher.AntMatcher;
import rip.deadcode.abukuma3.route.matcher.MatchResult;
import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.Service;

import static com.google.common.base.Preconditions.checkNotNull;

public final class AntRouter implements Router {

    private final AntMatcher matcher = new AntMatcher();
    private final String pattern;
    private Service service;

    public AntRouter(String pattern, Service service) {
        checkNotNull(pattern);
        checkNotNull(service);

        this.pattern = pattern;
        this.service = service;
    }

    @Override
    public RoutingResult proceed(Context context) {
        MatchResult matched = matcher.matches(pattern, context.getContextualPath());

        return matched.isMatched()
               ? new RoutingResultImpl(service.run(context.pathParam(matched.getParameters())))
               : RoutingResult.notMatched();
    }

}
