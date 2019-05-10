package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.router.RoutingContext;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.util.List;


public class RoutingContextImpl implements RoutingContext {

    private final AbuRequestHeader header;
    private final List<String> path;
    private final List<String> contextPath;

    public RoutingContextImpl( AbuRequestHeader header, String path, String contextPath ) {
        this.header = header;
        this.path = RoutingUtils.pathSplitter.splitToList( path );
        this.contextPath = RoutingUtils.pathSplitter.splitToList( contextPath );
    }

    public RoutingContextImpl( AbuRequestHeader header, List<String> path, List<String> contextPath ) {
        this.header = header;
        this.path = path;
        this.contextPath = contextPath;
    }

    @Override
    public AbuRequestHeader header() {
        return header;
    }

    @Override
    public List<String> path() {
        return path;
    }

    @Override
    public List<String> contextPath() {
        return contextPath;
    }

    @Override
    public RoutingContext contextPath( List<String> contextPath ) {
        return new RoutingContextImpl( header, path, contextPath );
    }
}
