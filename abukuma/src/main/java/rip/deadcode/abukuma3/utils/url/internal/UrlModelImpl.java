package rip.deadcode.abukuma3.utils.url.internal;

import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.UrlModel;
import rip.deadcode.abukuma3.utils.url.UrlQuery;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


public final class UrlModelImpl implements UrlModel {

    @Nullable private String scheme;
    @Nullable String username;
    @Nullable String password;
    @Nullable String host;
    /** Negative number for the empty port. */
    int port;
    PersistentList<String> path;
    @Nullable UrlQuery query;
    @Nullable String fragment;

    public UrlModelImpl(
            @Nullable String scheme,
            @Nullable String username,
            @Nullable String password,
            @Nullable String host,
            int port,
            List<String> path,
            @Nullable UrlQuery query,
            @Nullable String fragment
    ) {
        this.scheme = scheme;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = PersistentCollections.wrapList( path );
        this.query = query;
        this.fragment = fragment;
    }

    private UrlModelImpl copy() {
        return new UrlModelImpl(
                scheme,
                username,
                password,
                host,
                port,
                path,
                query,
                fragment
        );
    }

    @Override public Optional<String> scheme() {
        return Optional.ofNullable( scheme );
    }

    @Override public UrlModel scheme( String scheme ) {
        UrlModelImpl copy = copy();
        copy.scheme = scheme;
        return copy;
    }

    @Override public Optional<String> username() {
        return Optional.ofNullable( username );
    }

    @Override public UrlModel username( String username ) {
        UrlModelImpl copy = copy();
        copy.username = username;
        return copy;
    }

    @Override public Optional<String> password() {
        return Optional.ofNullable( password );
    }

    @Override public UrlModel password( String password ) {
        UrlModelImpl copy = copy();
        copy.password = password;
        return copy;
    }

    @Override public Optional<String> host() {
        return Optional.ofNullable( host );
    }

    @Override public UrlModel host( String host ) {
        UrlModelImpl copy = copy();
        copy.host = host;
        return copy;
    }

    @Override public OptionalInt port() {
        return port < 0
               ? OptionalInt.empty()
               : OptionalInt.of( port );
    }

    @Override public UrlModel port( int port ) {
        UrlModelImpl copy = copy();
        copy.port = port;
        return copy;
    }

    @Override public PersistentList<String> path() {
        return path;
    }

    @Override public UrlModel path( List<String> path ) {
        UrlModelImpl copy = copy();
        copy.path = PersistentCollections.wrapList( path );
        return copy;
    }

    @Override public Optional<UrlQuery> query() {
        return Optional.ofNullable( query );
    }

    @Override public UrlModel query( UrlQuery query ) {
        UrlModelImpl copy = copy();
        copy.query = query;
        return copy;
    }

    @Override public Optional<String> fragment() {
        return Optional.ofNullable( fragment );
    }

    @Override public UrlModel fragment( String fragment ) {
        UrlModelImpl copy = copy();
        copy.fragment = fragment;
        return copy;
    }
}
