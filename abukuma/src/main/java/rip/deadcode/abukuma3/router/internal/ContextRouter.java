package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.router.AbuRouter;
import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.router.RoutingResult;

import java.util.List;


public final class ContextRouter implements AbuRouter {

    private final List<String> contextPathPattern;
    private final AbuRouter delegate;

    public ContextRouter( String contextPathPattern, AbuRouter delegate ) {
        this.contextPathPattern = RoutingUtils.pathSplitter.splitToList( contextPathPattern );
        this.delegate = delegate;
    }

    @Override
    public RoutingResult route( RoutingContext context ) {
        List<String> contextPath = context.contextPath();
        return match( this.contextPathPattern, contextPath )
               ? delegate.route(
                context.contextPath( contextPath.subList( contextPathPattern.size(), contextPath.size() ) ) )
               : null;
    }

    private boolean match( List<String> pattern, List<String> path ) {
        if ( pattern.size() > path.size() ) {
            return false;
        }

        for ( int i = 0; i < pattern.size(); i++ ) {
            String patternSegment = pattern.get( i );
            String pathSegment = path.get( i );
            if ( !patternSegment.equals( pathSegment ) ) {
                return false;
            }
        }
        return true;
    }
}
