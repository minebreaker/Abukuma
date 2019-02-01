package rip.deadcode.abukuma3.utils;

import java.net.URI;

import static rip.deadcode.akashi.util.Uncheck.tryUncheck;

public final class UriBuilder {

    private boolean strict;
    private boolean rectify;

    private String scheme;
    private String authority;
    private String path;
    private String query;
    private String fragment;

    private UriBuilder( boolean strict, boolean rectify ) {
        this.strict = strict;
        this.rectify = rectify;
    }

    public static UriBuilder builder( boolean strict, boolean rectify ) {
        return new UriBuilder( strict, rectify );
    }



    public URI build() {
        return tryUncheck( () -> new URI( scheme, authority, path, query, fragment ) );
    }
}
