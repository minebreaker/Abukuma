package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.filter.internal.AntiCsrfHeaderFilter;
import rip.deadcode.abukuma3.filter.internal.BasicAuthFilter;
import rip.deadcode.abukuma3.filter.internal.LoggingFilter;

import java.util.function.Predicate;


public final class AbuFilters {

    public static AbuFilter noop() {
        return ( r, h ) -> h.handle( r );
    }

    public static AbuFilter logging() {
        return LoggingFilter.singleton;
    }

    public static AbuFilter antiCsrf() {
        return AntiCsrfHeaderFilter.singleton;
    }

    public static AbuFilter basicAuth(Predicate<AuthRequest> accepts) {
        return new BasicAuthFilter(accepts);
    }
}
