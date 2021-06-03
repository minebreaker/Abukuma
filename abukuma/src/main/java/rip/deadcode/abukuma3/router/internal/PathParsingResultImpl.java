package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.router.PathParsingResult;

import java.util.List;


public final class PathParsingResultImpl implements PathParsingResult {

    private final List<String> paths;
    private final String rest;

    public PathParsingResultImpl( List<String> paths, String rest ) {
        this.paths = paths;
        this.rest = rest;
    }

    @Override public List<String> paths() {
        return paths;
    }

    @Override public String rest() {
        return rest;
    }
}
