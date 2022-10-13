package rip.deadcode.abukuma3.filter.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;


public final class NoopFilter implements Filter {

    public static final NoopFilter singleton = new NoopFilter();

    @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {
        return null;
    }

    @Override public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
        return null;
    }
}
