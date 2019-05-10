package rip.deadcode.abukuma3.router.internal;


import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;
import java.util.function.Function;


public final class MappingRouter implements AbuRouter {

    private final String mappingRootPath;
    private final String resourceRootPath;
    private final Function<String, AbuHandler> resourceProvider;

    public MappingRouter(
            String mappingRootPath,
            String resourceRootPath,
            Function<String, AbuHandler> resourceProvider
    ) {
        this.mappingRootPath = mappingRootPath.startsWith( "/" ) ? mappingRootPath.substring( 1 ) : mappingRootPath;
        this.resourceRootPath = resourceRootPath.endsWith( "/" ) ? resourceRootPath : resourceRootPath + "/";
        this.resourceProvider = resourceProvider;
    }

    @Nullable @Override public RoutingResult route( RoutingContext context ) {

        // TODO refactoring
        String requestPath = String.join( "/", context.contextPath() );
        if ( !requestPath.startsWith( mappingRootPath ) ) {
            return null;
        }

        String resourceLocation = resourceRootPath + requestPath.substring( mappingRootPath.length() );
        return new RoutingResultImpl( resourceProvider.apply( resourceLocation ), ImmutableMap.of() );
    }
}
