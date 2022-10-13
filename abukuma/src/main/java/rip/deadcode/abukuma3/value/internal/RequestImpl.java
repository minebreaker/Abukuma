package rip.deadcode.abukuma3.value.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.net.URI;
import java.util.Optional;


public final class RequestImpl<T> implements Request<T> {

    private final T body;
    private final RequestHeader header;

    public RequestImpl(
            T body,
            RequestHeader header
    ) {
        this.body = body;
        this.header = header;
    }

    @Override public T body( ) {
        return body;
    }

    @Override public RequestHeader header() {
        return header;
    }

    @Override public String method() {
        return header().method();
    }

    @Override public URI url() {
        return header().url();
    }

    @Override public String urlString() {
        return header().urlString();
    }

    @Override public Optional<String> pathParam( String key ) {
        // FIXME
        throw new UnsupportedOperationException();
    }

    @Override public PersistentMap<String, String> pathParams() {
        // FIXME
        throw new UnsupportedOperationException();
    }

    @Override public Optional<String> queryParam( String key ) {
        // FIXME
        throw new UnsupportedOperationException();
    }

    @Override public PersistentMultimap<String, String> queryParams() {
        // FIXME
        throw new UnsupportedOperationException();
    }

    @Override public Optional<String> host() {
        // FIXME
        throw new UnsupportedOperationException();
    }

    @Override public String remoteAddress() {
        // FIXME
        throw new UnsupportedOperationException();
    }

    @Override public Object rawRequest() {
        return header.rawRequest();
    }

    @Override public Object rawResponse() {
        // FIXME
        throw new UnsupportedOperationException();
    }
}
