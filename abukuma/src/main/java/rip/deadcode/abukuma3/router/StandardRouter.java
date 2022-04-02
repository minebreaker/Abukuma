package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.Handler;


/**
 * {@link Router} with fluent interface to build matching patterns.
 */
public interface StandardRouter extends Router {

    public StandardRouter path( String method, String pattern, Handler handler );

    public StandardRouter ofMatcher(RouteMatcher matcher, Handler handler);

    @Override public StandardRouter notFound( Handler handler );
}
