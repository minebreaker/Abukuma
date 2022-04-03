package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.Router;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.incrementExact;


public final class PathMatchingRouterImpl implements Router {

    private final RouteMatcher matcher;
    private final Handler handler;
    @Nullable private final Handler notFound;

    public PathMatchingRouterImpl( String method, String pattern, Handler handler, @Nullable Handler notFound ) {
        this.handler = handler;
        this.notFound = notFound;

        this.matcher = new MethodMatcher( method )
                .and( parse( pattern ) );
    }

    private static RouteMatcher parse( String pattern ) {
        if ( Objects.equals( pattern, "*" ) ) {
            // OPTIONS
            return new ExactPathMatcher( "*" );
        } else {
            return parsePattern( pattern, 0 );
        }
    }

    private static final Pattern regexPart = Pattern.compile( "^/([^/]*)(.*)$" );

    /**
     * @param pattern     Route pattern currently matching to
     * @param targetIndex An index returned PathMatcher use
     */
    private static RouteMatcher parsePattern( String pattern, int targetIndex ) {
        Matcher m = regexPart.matcher( pattern );
        if ( !m.matches() ) {
            throw new IllegalStateException( "Invalid pattern: " + pattern );
        }

        String targetPattern = m.group( 1 );

        RouteMatcher resultMatcher = Optional.ofNullable( parsePathParameterPart( targetPattern, targetIndex ) )
                                             .orElse( new PathMatcher( targetPattern, targetIndex ) );

        String rest = m.group( 2 );
        if ( rest.isEmpty() ) {
            // if this is the final path segment, check the size of path segments to ensure there's no remaining
            return resultMatcher.and( new PathSegmentSizeMatcher( incrementExact( targetIndex ) ) );
        } else {
            return resultMatcher.and( parsePattern( rest, incrementExact( targetIndex ) ) );
        }
    }

    @Nullable private static RouteMatcher parsePathParameterPart( String pattern, int targetIndex ) {
        if ( pattern.startsWith( "{" ) && pattern.endsWith( "}" ) ) {
            return new PathVariableMatcher(
                    pattern.substring( 1, pattern.length() - 1 ),
                    targetIndex
            );
        }

        return null;
    }

    @Nullable @Override public RoutingResult route( RoutingContext context ) {

        PersistentMap<String, String> pathParams = matcher.matches( context );
        if ( pathParams == null ) {
            return null;
        }

        return new RoutingResultImpl( handler, pathParams );
    }

    @Nullable @Override public Handler notFound() {
        return notFound;
    }
}
