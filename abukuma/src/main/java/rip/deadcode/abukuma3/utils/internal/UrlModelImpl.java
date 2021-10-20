package rip.deadcode.abukuma3.utils.internal;

import rip.deadcode.abukuma3.utils.UrlModel;
import rip.deadcode.abukuma3.utils.UrlQuery;

import java.net.URI;
import java.util.Optional;
import java.util.OptionalInt;


public final class UrlModelImpl implements UrlModel {

    @Override public Optional<String> scheme() {
        return Optional.empty();
    }

    @Override public UrlModel scheme( String scheme ) {
        return null;
    }

    @Override public Optional<String> username() {
        return Optional.empty();
    }

    @Override public UrlModel username( String username ) {
        return null;
    }

    @Override public Optional<String> password() {
        return Optional.empty();
    }

    @Override public UrlModel password( String password ) {
        return null;
    }

    @Override public String host() {
        return null;
    }

    @Override public UrlModel host( String host ) {
        return null;
    }

    @Override public OptionalInt port() {
        return null;
    }

    @Override public UrlModel port( int port ) {
        return null;
    }

    @Override public String path() {
        return null;
    }

    @Override public UrlModel path( String path ) {
        return null;
    }

    @Override public UrlQuery query() {
        return null;
    }

    @Override public UrlModel query( UrlQuery query ) {
        return null;
    }

    @Override public String fragment() {
        return null;
    }

    @Override public UrlModel fragment( String fragment ) {
        return null;
    }

    @Override public String serialize() {
        return null;
    }

    @Override public URI asJava() {
        return null;
    }
}
