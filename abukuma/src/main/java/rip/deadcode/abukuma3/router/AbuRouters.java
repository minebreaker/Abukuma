package rip.deadcode.abukuma3.router;

import com.google.common.base.Splitter;
import com.google.mu.util.stream.BiStream;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.request.AbuRequestHeader;
import rip.deadcode.abukuma3.response.AbuResponse;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static rip.deadcode.akashi.lang.Languages.TODO;

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

        private static final AbuHandler defaultNotFoundHandler = request -> {
            return new AbuResponse( "" );  // TODO
        };

        private List<Route> handlers = new ArrayList<>();
        private AbuHandler notFound;

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
            this.notFound = handler;
            return this;
        }

        private static boolean matchesRoute( AbuRequestHeader requestHeader, @Nullable String method, String pattern ) {
            boolean methodMatches = method == null || requestHeader.getMethod().equalsIgnoreCase( method );
            if ( !methodMatches ) return false;

            // TODO refactoring
            Splitter s = Splitter.on( "/" ).omitEmptyStrings();
            List<String> url = s.splitToList( requestHeader.getRequestUrl() );
            List<String> patternList = s.splitToList( pattern );

            // If the sizes differ, immediately return false. Note BiStream.zip can be used with different sized streams.
            if ( url.size() != patternList.size() ) return false;

            return BiStream.zip( url.stream(), patternList.stream() )
                           .allMatch( ( p, u ) -> isMatch( p, u ) );
        }

        private static boolean isMatch( String pattern, String urlElement ) {
            if ( !pattern.startsWith( ":" ) ) {
                return pattern.equals( urlElement );
            } else {
                TODO();  // TODO
                return false;
            }
        }

        public AbuRouter build() {
            return request -> {

                Optional<Route> route = handlers.stream()
                                                .filter( candidate -> matchesRoute( request, candidate.method, candidate.pattern ) )
                                                .findAny();

                return route.isPresent()
                       ? route.get().handler
                       : notFound == null
                         ? defaultNotFoundHandler
                         : notFound;
            };
        }
    }
}
