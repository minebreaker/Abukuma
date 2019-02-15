package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.filter.internal.AntiCsrfHeaderFilter;
import rip.deadcode.abukuma3.filter.internal.LoggingFilter;


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
}
