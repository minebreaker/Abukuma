package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.filter.internal.AntiCsrfHeaderFilter;
import rip.deadcode.abukuma3.filter.internal.BasicAuthFilter;
import rip.deadcode.abukuma3.filter.internal.LoggingFilter;

import java.util.function.Predicate;


public final class Filters {

    private Filters() {
        throw new Error();
    }

    public static Filter noop() {
        return ( c, r, h ) -> h.handle( c, r );
    }

    public static Filter logging() {
        return LoggingFilter.singleton;
    }

    public static Filter antiCsrf() {
        return AntiCsrfHeaderFilter.singleton;
    }

    public static Filter basicAuth( Predicate<AuthRequest> accepts ) {
        return new BasicAuthFilter( accepts );
    }
}
