package rip.deadcode.abukuma3.router;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.internal.utils.MoreCollections;
import rip.deadcode.abukuma3.internal.utils.Resources;
import rip.deadcode.abukuma3.router.internal.RoutingContextImpl;
import rip.deadcode.abukuma3.router.internal.UriHandler;
import rip.deadcode.abukuma3.router.internal.UriRootHandler;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiPredicate;

import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.also;


// TODO refactoring all
public final class AbuRouters {

    public static AbuRouterBuilder builder() {
        return new AbuRouterBuilder();
    }

    @NotThreadSafe
    public static final class AbuRouterBuilder {

        private static class Route {}

        private static final class PathSegmentRoute extends Route {

            @Nullable private final String method;
            private final String pattern;
            private final AbuHandler handler;

            private PathSegmentRoute( String method, String pattern, AbuHandler handler ) {
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

        private static final AbuRoutingContext defaultNotFound = new RoutingContextImpl(
                ImmutableMap.of(),
                request -> AbuResponse.create( "<h1>404 Not Found</h1>" ).header( h -> h.contentType( "text/html" ) )  // TODO
        );

        private final List<Route> mappings = new ArrayList<>();
        @Nullable private AbuRoutingContext notFound;

        public AbuRouterBuilder path( String pattern, AbuHandler handler ) {
            mappings.add( new PathSegmentRoute( null, pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder get( String pattern, AbuHandler handler ) {
            mappings.add( new PathSegmentRoute( "GET", pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder post( String pattern, AbuHandler handler ) {
            mappings.add( new PathSegmentRoute( "POST", pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder file( String mappingPath, Path servingPath ) {
            mappings.add( new PathSegmentRoute( "GET", mappingPath, UriHandler.create( () -> servingPath.toUri() ) ) );
            return this;
        }

        public AbuRouterBuilder dir( String mappingRootPath, Path directoryBase ) {
            mappings.add( new MatcherRoute(
                    rootMatcher( mappingRootPath ),
                    UriRootHandler.create( mappingRootPath, () -> mappingRootPath, uri -> directoryBase.resolve( uri ).toUri() )
            ) );
            return this;
        }

        public AbuRouterBuilder resource( String mappingPath, String resourcePath ) {
            mappings.add( new PathSegmentRoute( "GET", mappingPath, UriHandler.create( () -> Resources.grabResource( resourcePath ) ) ) );
            return this;
        }

        public AbuRouterBuilder resources( String mappingRootPath, String resourceBase ) {
            String resourceBaseDir = resourceBase.endsWith( "/" ) ? resourceBase : resourceBase + "/";
            mappings.add( new MatcherRoute(
                    rootMatcher( mappingRootPath ),
                    UriRootHandler.create( mappingRootPath, () -> resourceBaseDir, uri -> Resources.grabResource( uri ) )
            ) );
            return this;
        }

        private static BiPredicate<String, String> rootMatcher( String pattern ) {
            return ( method, url ) -> method.equalsIgnoreCase( "GET" )
                                      && ( url.equals( pattern ) || ( url + "/" ).startsWith( pattern ) );
        }

        public AbuRouterBuilder notFound( AbuHandler handler ) {
            this.notFound = new RoutingContextImpl( ImmutableMap.of(), handler );
            return this;
        }

        @Nullable
        private static AbuRoutingContext matchesRoute( AbuRequestHeader requestHeader, Route route ) {
            if ( route instanceof PathSegmentRoute ) {
                return matchesPathSegment( requestHeader, (PathSegmentRoute) route );
            } else if ( route instanceof MatcherRoute ) {
                return matchesMatcherRoute( requestHeader, (MatcherRoute) route );
            } else {
                throw new Error();  // This won't happen
            }
        }

        private static AbuRoutingContext matchesPathSegment( AbuRequestHeader requestHeader, PathSegmentRoute route ) {

            boolean methodMatches = route.method == null || requestHeader.method().equalsIgnoreCase( route.method );
            if ( !methodMatches ) return null;

            Splitter s = Splitter.on( "/" ).omitEmptyStrings();
            List<String> requestPathSegments = ImmutableList.copyOf( s.split( requestHeader.requestUri() ) );
            List<String> patternList = ImmutableList.copyOf( s.split( route.pattern ) );

            // If the sizes differ, immediately return false. Note BiStream.zip can be used with different sized streams.
            if ( requestPathSegments.size() != patternList.size() ) return null;

            List<Map<String, String>> result = also(
                    new ArrayList<>(),
                    e -> Iterables.addAll( e, MoreCollections.zip( patternList, requestPathSegments, AbuRouterBuilder::matchEachSegment ) )
            );

            if ( result.stream().allMatch( e -> e != null ) ) {
                Map<String, String> params = result.stream().collect( HashMap::new, Map::putAll, Map::putAll );
                return new RoutingContextImpl( ImmutableMap.copyOf( params ), route.handler );
            } else {
                return null;
            }
        }

        @Nullable
        private static Map<String, String> matchEachSegment( String pattern, String urlElement ) {
            if ( pattern.equals( "*" ) ) {
                return ImmutableMap.of();
            } else if ( pattern.startsWith( ":" ) ) {
                String key = pattern.substring( 1 );
                return ImmutableMap.of( key, urlElement );
            } else {
                return pattern.equals( urlElement ) ? ImmutableMap.of() : null;
            }
        }

        @Nullable
        private static AbuRoutingContext matchesMatcherRoute( AbuRequestHeader requestHeader, MatcherRoute route ) {

            if ( route.pathMatcher.test( requestHeader.method(), requestHeader.requestUri() ) )
                return new RoutingContextImpl( ImmutableMap.of(), route.handler );
            else {
                return null;
            }
        }

        public AbuRouter build() {
            return request -> {

                Optional<AbuRoutingContext> route = mappings.stream()
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
