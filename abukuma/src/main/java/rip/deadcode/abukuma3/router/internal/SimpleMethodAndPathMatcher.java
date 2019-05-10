package rip.deadcode.abukuma3.router.internal;

import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.router.RouteMatcher;
import rip.deadcode.abukuma3.router.RoutingContext;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.zipToList;


public final class SimpleMethodAndPathMatcher implements RouteMatcher {

    private final String method;
    private final List<String> pathPattern;

    public SimpleMethodAndPathMatcher( String method, String path ) {
        this.method = method;
        this.pathPattern = RoutingUtils.pathSplitter.splitToList( path );
    }

    @Nullable
    @Override
    public Map<String, String> matches( RoutingContext context ) {

        return method.equals( context.header().method() )
               ? matchesAll( pathPattern, context.contextPath() )
               : null;
    }

    @Nullable
    private static Map<String, String> matchesAll( List<String> pattern, List<String> path ) {

        // If the sizes differ, immediately return null.
        if ( pattern.size() != path.size() ) return null;

        List<Map<String, String>> result = zipToList(
                pattern, path, SimpleMethodAndPathMatcher::matchesEachSegment );

        if ( result.stream().allMatch( Objects::nonNull ) ) {
            return result.stream().collect( HashMap::new, Map::putAll, Map::putAll );
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
    private static Map<String, String> matchesEachSegment( String pattern, String segment ) {
        if ( pattern.equals( "*" ) ) {
            return ImmutableMap.of();
        } else if ( pattern.startsWith( ":" ) ) {
            String key = pattern.substring( 1 );
            return ImmutableMap.of( key, segment );
        } else {
            return pattern.equals( segment ) ? ImmutableMap.of() : null;
        }
    }
}
