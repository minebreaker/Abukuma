package rip.deadcode.abukuma3.router.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.Routers;
import rip.deadcode.abukuma3.router.RouterSpec;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.addLast;


public class RouterSpecImpl implements RouterSpec {

    private final List<Router> routers;
    @Nullable private final Router notFound;

    public RouterSpecImpl() {
        this( ImmutableList.of(), null );
    }

    private RouterSpecImpl( List<Router> routers, Router notFound ) {
        this.routers = routers;
        this.notFound = notFound;
    }

    private RouterSpec add( Router router ) {
        return new RouterSpecImpl(
                addLast( routers, router ),
                notFound
        );
    }

    @Override public RouterSpec path( String method, String pattern, Handler handler ) {
        // TODO DI matcher to use
        return add( new BasicRouter( new RouteImpl( new SimpleMethodAndPathMatcher( method, pattern ), handler ) ) );
    }

    @Override public RouterSpec get( String pattern, Handler handler ) {
        return path( "GET", pattern, handler );
    }

    @Override public RouterSpec post( String pattern, Handler handler ) {
        return path( "POST", pattern, handler );
    }

    @Override public RouterSpec notFound( Handler handler ) {
        return new RouterSpecImpl( routers, context -> new RoutingResultImpl( handler, ImmutableMap.of() ) );
    }

    @Override public RouterSpec file( String mappingPath, Path file ) {
        return add( context -> {
            if ( Files.notExists( file ) ) {
                return null;
            }
            Handler handler = new PathHandler( file );
            return new RoutingResultImpl( handler, ImmutableMap.of() );
        } );
    }

    @Override public RouterSpec dir( String mappingRoutePath, Path root ) {
        return add( new MappingRouter(
                mappingRoutePath,
                root.toString(),
                path -> new PathHandler( root.getFileSystem().getPath( path ) )
        ) );
    }

    @Override public RouterSpec resource( String mappingPath, String resource ) {

        String normalizedMappingPath = mappingPath.startsWith( "/" ) ? mappingPath.substring( 1 ) : mappingPath;
        return add( context -> {
            String requestPath = String.join( "/", context.contextPath() );
            if ( !requestPath.equals( normalizedMappingPath ) ) {
                return null;
            }

            return new RoutingResultImpl( new ResourceHandler( resource ), ImmutableMap.of() );
        } );
    }

    @Override public RouterSpec resources( String mappingRootPath, String resourceRootPath ) {
        return add( new MappingRouter( mappingRootPath, resourceRootPath, ResourceHandler::new ) );
    }

    @Override public RouterSpec context( String contextPath, Function<RouterSpec, RouterSpec> router ) {
        return add( new ContextRouter( contextPath, router.apply( Routers.create() ).createRouter() ) );
    }

    @Override public Router createRouter() {
        List<Router> allRouters = notFound == null ? routers : addLast( routers, notFound );
        //noinspection OptionalGetWithoutIsPresent
        return allRouters.stream().reduce( Router::ifNotMatch ).get();
    }
}
