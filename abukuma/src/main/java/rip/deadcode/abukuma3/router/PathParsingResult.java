package rip.deadcode.abukuma3.router;


import java.util.List;


public interface PathParsingResult {

    /**
     * This list may contains "/".
     *
     * Examples:
     * {@literal
     *   /foo/bar/buz -> "/" "foo" "/" "bar" "/" "buz"
     *   //slash// -> "/" "/" "slash" "/" "/"
     * }
     *
     * @return List of parsed URL path
     */
    public List<String> paths();

    public String rest();
}
