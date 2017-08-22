package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.service.Context;

import javax.annotation.Nullable;
import java.util.Optional;

public final class RoutingResultImpl implements RoutingResult {

    private final Context ctx;

    public RoutingResultImpl(@Nullable Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean routeMatched() {
        return ctx != null;
    }

    @Override public Optional<Context> result() {
        return Optional.ofNullable(ctx);
    }

}
