package rip.deadcode.abukuma3.utils.url;

public final class UrlBuilder {

    private String scheme;
    private String authority;
    private String path;
    private String query;
    private String fragment;

    public static UrlBuilder builder() {
        return new UrlBuilder();
    }

    public String build() {
        throw new UnsupportedOperationException();
    }

    public UrlBuilder scheme( String scheme ) {
        this.scheme = scheme;
        return this;
    }

    public final class Option {
    }
}
