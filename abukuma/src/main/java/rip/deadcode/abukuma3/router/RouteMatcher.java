package rip.deadcode.abukuma3.router;

import javax.annotation.Nullable;
import java.util.Map;


@FunctionalInterface
public interface RouteMatcher {

    @Nullable
    public Map<String, String> matches( RoutingContext context );
}
