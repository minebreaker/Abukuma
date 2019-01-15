package rip.deadcode.abukuma3.router;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.mu.util.stream.BiStream;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.router.internal.RoutingContextImpl;
import rip.deadcode.abukuma3.router.internal.UriHandler;
import rip.deadcode.abukuma3.router.internal.UriRootHandler;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiPredicate;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

// TODO refactoring all
public final class AbuRouters {

    public static AbuRouterBuilder builder() {
        return new AbuRouterBuilder();
    }

    @NotThreadSafe
    public static final class AbuRouterBuilder {

        private static class Route {}

        private static final class PathRoute extends Route {

            @Nullable private final String method;
            private final String pattern;
            private final AbuHandler handler;

            private PathRoute( String method, String pattern, AbuHandler handler ) {
                this.method = method;
                this.pattern = pattern;
                this.handler = handler;
            }
        }

        private static final class MatcherRoute extends Route {

            private final BiPredicate<String, String> pathMatcher;
            private final AbuHandler handler;

            private MatcherRoute( BiPredicate<String, String> pathMatcher, AbuHandler handler ) {
                this.pathMatcher = pathMatcher;
                this.handler = handler;
            }
        }

        private AbuRouterBuilder() {}

        private static final RoutingContext defaultNotFound = new RoutingContextImpl(
                ImmutableMap.of(),
                request -> {
                    return AbuResponse.create( "<h1>404 Not Found</h1>" ).header( h -> h.contentType( "text/html" ) );
                }
        );

        private List<Route> mappings = new ArrayList<>();
        @Nullable private RoutingContext notFound;

        public AbuRouterBuilder path( String pattern, AbuHandler handler ) {
            mappings.add( new PathRoute( null, pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder get( String pattern, AbuHandler handler ) {
            mappings.add( new PathRoute( "GET", pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder post( String pattern, AbuHandler handler ) {
            mappings.add( new PathRoute( "POST", pattern, handler ) );
            return this;
        }

        private static URI grabResource( String path ) {
            try {
                return checkNotNull( AbuRouters.class.getClassLoader().getResource( path ) ).toURI();
            } catch ( URISyntaxException e ) {
                throw new RuntimeException( e );
            }
        }

        public AbuRouterBuilder file( String mappingPath, Path servingPath ) {
            mappings.add( new PathRoute( "GET", mappingPath, UriHandler.create( servingPath.toUri() ) ) );
            return this;
        }

        public AbuRouterBuilder resource( String mappingPath, String resourcePath ) {
            mappings.add( new PathRoute( "GET", mappingPath, UriHandler.create( grabResource( resourcePath ) ) ) );
            return this;
        }

        private static BiPredicate<String, String> rootMatcher( String pattern ) {
            return ( method, url ) -> method.equalsIgnoreCase( "GET" )
                                      && url.equals( pattern ) || ( url + "/" ).startsWith( pattern );
        }

        public AbuRouterBuilder dir( String mappingRootPath, Path directoryBase ) {
            mappings.add( new MatcherRoute( rootMatcher( mappingRootPath ), UriRootHandler.create( directoryBase.toUri() ) ) );
            return this;
        }

        public AbuRouterBuilder resources( String mappingRootPath, String resourceBase ) {
            mappings.add( new MatcherRoute( rootMatcher( mappingRootPath ), UriRootHandler.create( grabResource( resourceBase ) ) ) );
            return this;
        }

        public AbuRouterBuilder notFound( AbuHandler handler ) {
            this.notFound = new RoutingContextImpl( ImmutableMap.of(), handler );
            return this;
        }

        @Nullable
        private static RoutingContext matchesRoute( AbuRequestHeader requestHeader, Route route ) {
            if ( route instanceof PathRoute ) {
                return matchesPathRoute( requestHeader, (PathRoute) route );
            } else if ( route instanceof MatcherRoute ) {
                MatcherRoute mRoute = (MatcherRoute) route;
                if ( mRoute.pathMatcher.test( requestHeader.method(), requestHeader.requestUrl() ) )
                    return new RoutingContextImpl( ImmutableMap.of(), mRoute.handler );
                else {
                    return null;
                }
            } else {
                throw new Error();  // This won't happen
            }
        }

        private static RoutingContext matchesPathRoute( AbuRequestHeader requestHeader, PathRoute route ) {

            boolean methodMatches = route.method == null || requestHeader.method().equalsIgnoreCase( route.method );
            if ( !methodMatches ) return null;

            Splitter s = Splitter.on( "/" ).omitEmptyStrings();
            List<String> url = s.splitToList( requestHeader.requestUrl() );
            List<String> patternList = s.splitToList( route.pattern );

            // If the sizes differ, immediately return false. Note BiStream.zip can be used with different sized streams.
            if ( url.size() != patternList.size() ) return null;

            List<Map<String, String>> result =
                    BiStream.zip( patternList.stream(), url.stream() )
                            .map( ( p, u ) -> matchEach( p, u ) )
                            .collect( toList() );
            if ( result.stream().allMatch( e -> e != null ) ) {
                Map<String, String> params = result.stream().collect( HashMap::new, Map::putAll, Map::putAll );
                return new RoutingContextImpl( ImmutableMap.copyOf( params ), route.handler );
            } else {
                return null;
            }
        }

        @Nullable
        private static Map<String, String> matchEach( String pattern, String urlElement ) {
            if ( pattern.equals( "*" ) ) {
                return ImmutableMap.of();
            } else if ( pattern.startsWith( ":" ) ) {
                String key = pattern.substring( 1 );
                return ImmutableMap.of( key, urlElement );
            } else {
                return pattern.equals( urlElement ) ? ImmutableMap.of() : null;
            }
        }

        public AbuRouter build() {
            return request -> {

                Optional<RoutingContext> route = mappings.stream()
                                                         .map( candidate -> matchesRoute( request, candidate ) )
                                                         .filter( candidate -> candidate != null )
                                                         .findAny();

                if ( route.isPresent() ) {
                    return route.get();

                } else if ( notFound != null ) {
                    return notFound;
                } else {
                    return defaultNotFound;
                }
            };
        }
    }
}
