package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.filter.internal.AntiCsrfHeaderFilter;


public final class AbuFilters {

    public static AbuFilter antiCsrf() {
        return AntiCsrfHeaderFilter.singleton;
    }
}
