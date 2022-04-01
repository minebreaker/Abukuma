package rip.deadcode.abukuma3.value.internal;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Optional;


public final class RequestImpl implements Request {

    private final ExecutionContext context;

    private final InputStream is;
    private final Object body;
    private final String method;
    private final RequestHeader header;

    public RequestImpl(
            ExecutionContext context,
            InputStream is,  // FIXME: refactoring
            Object body,
            String method,
            RequestHeader header
    ) {
        this.context = context;
        this.is = is;
        this.body = body;
        this.method = method;
        this.header = header;
    }

    @Override public <T> T body( Class<T> cls ) {
        // FIXME
        try {
            return (T) context.parser().parse( cls, is, header );
        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
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

    @Override public RequestHeader header() {
        return header;
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
