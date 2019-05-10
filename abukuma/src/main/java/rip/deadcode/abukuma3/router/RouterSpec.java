package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.AbuHandler;

import java.nio.file.Path;
import java.util.function.Function;


public interface RouterSpec {

    public RouterSpec path( String method, String pattern, AbuHandler handler );

    public RouterSpec get( String pattern, AbuHandler handler );

    public RouterSpec post( String pattern, AbuHandler handler );

    public RouterSpec notFound( AbuHandler handler );

    public RouterSpec file( String mappingPath, Path file );

    public RouterSpec dir( String mappingRoutePath, Path root );

    public RouterSpec resource( String mappingPath, String location );

    public RouterSpec resources( String mappingRootPath, String resourceRootPath  );

    public RouterSpec context( String contextPath, Function<RouterSpec, RouterSpec> router );

    public AbuRouter createRouter();
}
