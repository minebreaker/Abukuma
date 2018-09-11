package rip.deadcode.abukuma3.router;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.mu.util.stream.BiStream;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.router.internal.RoutingContextImpl;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.akashi.collection.Tuple;
import rip.deadcode.akashi.collection.Tuples;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.*;

import static java.util.stream.Collectors.toList;

// TODO refactoring all
public final class AbuRouters {

    public static AbuRouterBuilder builder() {
        return new AbuRouterBuilder();
    }

    @NotThreadSafe
    public static final class AbuRouterBuilder {

        private static final class Route {

            @Nullable
            private final String method;
            private final String pattern;
            private final AbuHandler handler;

            private Route( String method, String pattern, AbuHandler handler ) {
                this.method = method;
                this.pattern = pattern;
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

        private List<Route> handlers = new ArrayList<>();
        private RoutingContext notFound;

        public AbuRouterBuilder path( String pattern, AbuHandler handler ) {
            handlers.add( new Route( null, pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder get( String pattern, AbuHandler handler ) {
            handlers.add( new Route( "GET", pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder post( String pattern, AbuHandler handler ) {
            handlers.add( new Route( "POST", pattern, handler ) );
            return this;
        }

        public AbuRouterBuilder notFound( AbuHandler handler ) {
            this.notFound = new RoutingContextImpl( ImmutableMap.of(), handler );
            return this;
        }

        @Nullable
        private static Tuple<Route, Map<String, String>> matchesRoute( AbuRequestHeader requestHeader, Route route ) {
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
                return Tuples.of( route, ImmutableMap.copyOf( params ) );
            } else {
                return null;
            }
        }

        @Nullable
        private static Map<String, String> matchEach( String pattern, String urlElement ) {
            if ( pattern.startsWith( ":" ) ) {
                String key = pattern.substring( 1 );
                return ImmutableMap.of( key, urlElement );
            } else {
                return pattern.equals( urlElement ) ? ImmutableMap.of() : null;
            }
        }

        public AbuRouter build() {
            return request -> {

                Optional<Tuple<Route, Map<String, String>>> route = handlers.stream()
                                                                            .map( candidate -> matchesRoute( request, candidate ) )
                                                                            .filter( candidate -> candidate != null )
                                                                            .findAny();

                return route.isPresent()
                       ? new RoutingContextImpl( route.get().getRight(), route.get().getLeft().handler )
                       : notFound == null
                         ? defaultNotFound
                         : notFound;
            };
        }
    }
}
