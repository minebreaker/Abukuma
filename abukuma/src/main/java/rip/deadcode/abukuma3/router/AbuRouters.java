package rip.deadcode.abukuma3.router;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.internal.utils.Resources;
import rip.deadcode.abukuma3.router.internal.*;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiPredicate;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.zipToList;


// TODO refactoring all
public final class AbuRouters {

    public static AbuRouterBuilder builder() {
        return new AbuRouterBuilder();
    }

    @NotThreadSafe
    public static final class AbuRouterBuilder {

        private static class Route {}

        private static final class PathSegmentRoute extends Route {

            private final String pattern;
            private final AbuHandler handler;

            private PathSegmentRoute( String pattern, AbuHandler handler ) {
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
            mappings.add( new PathSegmentRoute( pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder get( String pattern, AbuHandler handler ) {
            mappings.add( new PathSegmentRoute( pattern, new MethodCheckingHandler( "GET", handler ) ) );
            return this;
        }

        public AbuRouterBuilder post( String pattern, AbuHandler handler ) {
            mappings.add( new PathSegmentRoute( pattern, new MethodCheckingHandler( "POST", handler ) ) );
            return this;
        }

        public AbuRouterBuilder file( String mappingPath, Path servingPath ) {
            mappings.add( new PathSegmentRoute(
                    mappingPath,
                    new MethodCheckingHandler( "GET", UriHandler.create( () -> servingPath.toUri() ) )
            ) );
            return this;
        }

        public AbuRouterBuilder dir( String mappingRootPath, Path directoryBase ) {
            MatchingHandler handler = UriRootHandler.create( mappingRootPath, directoryBase.toString(), uri -> {
                Path path = Paths.get( uri );
                return Files.exists( path ) ? Optional.of( path.toUri() ) : Optional.empty();
            } );
            mappings.add( new MatcherRoute(
                    ( method, url ) -> handler.matches( url ),
                    new MethodCheckingHandler( "GET", handler )
            ) );
            return this;
        }

        public AbuRouterBuilder resource( String mappingPath, String resourcePath ) {
            mappings.add( new PathSegmentRoute(
                    mappingPath,
                    new MethodCheckingHandler( "GET", UriHandler.create( () -> Resources.grabResource( resourcePath ) ) )
            ) );
            return this;
        }

        // TODO need refactoring
        public AbuRouterBuilder resources( String mappingRootPath, String resourceBase ) {
            String resourceBaseDir = resourceBase.endsWith( "/" ) ? resourceBase : resourceBase + "/";
            MatchingHandler handler = UriRootHandler.create( mappingRootPath, resourceBaseDir, uri -> Resources.mayGrabResource( uri ) );
            mappings.add( new MatcherRoute(
                    ( method, url ) -> handler.matches( url ),
                    new MethodCheckingHandler( "GET", handler )
            ) );
            return this;
        }

        public AbuRouterBuilder notFound( AbuHandler handler ) {
            this.notFound = new RoutingContextImpl( ImmutableMap.of(), handler );
            return this;
        }

        @Nullable
        private static AbuRoutingContext matchesRoute( AbuRequestHeader requestHeader, Route route ) {
            if ( route instanceof PathSegmentRoute ) {
                return matchesPathSegments( requestHeader, (PathSegmentRoute) route );
            } else if ( route instanceof MatcherRoute ) {
                return matchesMatcherRoute( requestHeader, (MatcherRoute) route );
            } else {
                throw new Error();  // This won't happen
            }
        }

        private static final Splitter pathSplitter = Splitter.on( "/" ).omitEmptyStrings();

        private static AbuRoutingContext matchesPathSegments( AbuRequestHeader requestHeader, PathSegmentRoute route ) {

            List<String> requestPathSegments = ImmutableList.copyOf( pathSplitter.split( requestHeader.requestUri() ) );
            List<String> patternList = ImmutableList.copyOf( pathSplitter.split( route.pattern ) );

            // If the sizes differ, immediately return false.
            if ( requestPathSegments.size() != patternList.size() ) return null;

            List<Map<String, String>> result = zipToList(
                    patternList, requestPathSegments, AbuRouterBuilder::matchEachSegment );

            if ( result.stream().allMatch( e -> e != null ) ) {
                Map<String, String> params = result.stream().collect( HashMap::new, Map::putAll, Map::putAll );
                return new RoutingContextImpl( ImmutableMap.copyOf( params ), route.handler );
            } else {
                return null;
            }
        }

        /**
         * @param pattern URL path segment pattern
         * @param urlElement Actual URL path segment
         * @return Map that contains a pair of the path parameter name and the corresponding value.
         *         Empty map if the segment matches without the path variables.
         *         Null if the path does not match.
         */
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
