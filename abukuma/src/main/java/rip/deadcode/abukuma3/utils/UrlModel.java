package rip.deadcode.abukuma3.utils;

import java.net.URI;
import java.util.Optional;
import java.util.OptionalInt;


public interface UrlModel {

    public Optional<String> scheme();

    public UrlModel scheme( String scheme );

    public Optional<String> username();

    public UrlModel username( String username );

    public Optional<String> password();

    public UrlModel password( String password );

    public String host();

    public UrlModel host( String host );

    public OptionalInt port();

    public UrlModel port( int port );

    public String path();

    public UrlModel path( String path );

    public UrlQuery query();

    public UrlModel query(UrlQuery query);

    public String fragment();

    public UrlModel fragment(String fragment);

    public String serialize();

    public URI asJava();

    public static UrlModel parse() {
        throw new UnsupportedOperationException();
    }
}
