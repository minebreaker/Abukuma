package rip.deadcode.abukuma3.test;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.net.URI;
import java.util.Map;
import java.util.Optional;


public final class TestRequest implements AbuRequest {

    @Override public <T> T body( Class<T> cls ) {
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

    @Override public AbuRequestHeader header() {
        return null;
    }

    @Override public Optional<String> pathParam( String key ) {
        return Optional.empty();
    }

    @Override public Map<String, String> pathParams() {
        return null;
    }

    @Override public Optional<String> queryParam( String key ) {
        return Optional.empty();
    }

    @Override public Multimap<String, String> queryParams() {
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
