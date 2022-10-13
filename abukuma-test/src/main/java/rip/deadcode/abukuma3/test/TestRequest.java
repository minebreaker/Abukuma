package rip.deadcode.abukuma3.test;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.net.URI;
import java.util.Optional;


public final class TestRequest<T> implements Request<T> {

    @Override public T body() {
        return null;
    }

    @Override public String method() {
        return null;
    }

    @Override public URI url() {
        return null;
    }

    @Override public String urlString() {
        return null;
    }

    @Override public RequestHeader header() {
        return null;
    }

    @Override public Optional<String> pathParam( String key ) {
        return Optional.empty();
    }

    @Override public PersistentMap<String, String> pathParams() {
        return null;
    }

    @Override public Optional<String> queryParam( String key ) {
        return Optional.empty();
    }

    @Override public PersistentMultimap<String, String> queryParams() {
        return null;
    }

    @Override public Optional<String> host() {
        return Optional.empty();
    }

    @Override public String remoteAddress() {
        return null;
    }

    @Override public Object rawRequest() {
        throw new UnsupportedOperationException();
    }

    @Override public Object rawResponse() {
        throw new UnsupportedOperationException();
    }
}
