package rip.deadcode.abukuma3.utils;

import java.net.URI;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public final class UrlBuilder {

    private final boolean strict;
    private final boolean rectify;

    private String scheme;
    private String authority;
    private String path;
    private String query;
    private String fragment;

    private UrlBuilder( boolean strict, boolean rectify ) {
        this.strict = strict;
        this.rectify = rectify;
    }

    public static UrlBuilder builder( boolean strict, boolean rectify ) {
        return new UrlBuilder( strict, rectify );
    }

    public URI build() {
        return uncheck( () -> new URI( scheme, authority, path, query, fragment ) );
    }

    public UrlBuilder scheme( String scheme ) {
        this.scheme = scheme;
        return this;
    }
}
