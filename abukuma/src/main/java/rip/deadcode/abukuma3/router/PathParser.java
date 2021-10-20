package rip.deadcode.abukuma3.router;


/**
 * Parses the URI path.
 *
 * {@value urlPath} should be the `request-target` specified in the RFC7230 §5.1.2.
 */
@FunctionalInterface
public interface PathParser {

    public PathParsingResult parse( String urlPath );
}
