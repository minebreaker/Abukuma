package rip.deadcode.abukuma3.value.internal;

import com.google.common.net.MediaType;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Set;


// FIXME
public final class RequestHeaderImpl implements RequestHeader {

    private final ExecutionContext context;
    private final String method;
    private final URI uri;
    private final PersistentList<String> urlPaths;

    public RequestHeaderImpl(
            ExecutionContext context,
            String method,
            URI uri,
            PersistentList<String> urlPaths
    ) {
        this.context = context;
        this.method = method;
        this.uri = uri;
        this.urlPaths = urlPaths;
    }

    @Override public ExecutionContext context() {
        return context;
    }

    @Override public String method() {
        return method;
    }

    @Override public URI url() {
        return null;
    }

    @Override public String urlString() {
        return null;
    }

    @Override public PersistentList<String> urlPaths() {
        return urlPaths;
    }

    @Nullable @Override public String getValue( String headerName ) {
        return null;
    }

    @Override public Set<String> getValues( String headerName ) {
        return null;
    }

    @Override public Optional<String> mayGet( String headerName ) {
        return Optional.empty();
    }

    @Override public List<Cookie> cookie() {
        return null;
    }

    @Override public Optional<Cookie> cookie( String cookieName ) {
        return Optional.empty();
    }

    @Override public String contentType() {
        return null;
    }

    @Override public MediaType mediaType() {
        return null;
    }

    @Override public Optional<Charset> charset() {
        return Optional.empty();
    }

    @Override public Object rawRequest() {
        return null;
    }
}
