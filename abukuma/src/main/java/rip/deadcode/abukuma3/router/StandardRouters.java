package rip.deadcode.abukuma3.router;


import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.internal.PathMatchingRouterImpl;
import rip.deadcode.abukuma3.router.internal.StandardRouterImpl;


public final class StandardRouters {

    private StandardRouters() {
        throw new Error();
    }

    /**
     * <pre>
     *   pattern    = '*'
     *              | '/' part?
     *   part       = path_parameter_part
     *              | regex_part
     *              | glob_part
     *
     *   path_parameter_part = '{' [^}]* '}'
     *
     *   regex_part = ':' regex
     *   regex      = [^/]+
     *
     *   glob_part      = '?' | '*' | literal_letter
     *   literal_letter = [^/^*]
     * </pre>
     */
    public static StandardRouter path( String method, String pattern, Handler handler ) {
        return new StandardRouterImpl( new PathMatchingRouterImpl( method, pattern, handler, null ) );
    }
}
