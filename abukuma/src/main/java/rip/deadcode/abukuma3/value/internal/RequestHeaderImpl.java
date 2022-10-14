package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.Multimap;
import com.google.common.net.MediaType;
import org.jetbrains.annotations.Nullable;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapMultimap;


public final class RequestHeaderImpl implements RequestHeader {

    private final String method;
    private final URI url;
    private final PersistentList<String> urlPaths;
    private final PersistentMultimap<String, String> headerValues;
    private final PersistentList<Cookie> cookies;
    private final MediaType mediaType;
    private final Object rawRequest;

    public RequestHeaderImpl(
            String method,
            URI url,
            List<String> urlPaths,
            Multimap<String, String> headerValues,
            List<Cookie> cookies,
            MediaType mediaType,
            Object RawRequest
    ) {
        this.method = method;
        this.url = url;
        this.urlPaths = wrapList( urlPaths );
        this.headerValues = wrapMultimap( headerValues );
        this.cookies = wrapList( cookies );
        this.mediaType = mediaType;
        this.rawRequest = RawRequest;
    }

    @Override public ExecutionContext context() {
        throw new UnsupportedOperationException();
    }

    @Override public String method() {
        return method;
    }

    @Override public URI url() {
        return url;
    }

    @Override public String urlString() {
        return url().toString();
    }

    @Override public PersistentList<String> urlPaths() {
        return urlPaths;
    }

    @Nullable @Override public String getValue( String headerName ) {
        return headerValues.getValue( headerName );
    }

    @Override public PersistentList<String> getValues( String headerName ) {
        return headerValues.get( headerName );
    }

    @Override public Optional<String> mayGet( String headerName ) {
        return headerValues.mayGet( headerName );
    }

    @Override public PersistentList<Cookie> cookie() {
        return cookies;
    }

    @Override public Optional<Cookie> cookie( String cookieName ) {
        return cookies.stream().filter( c -> Objects.equals( c.name(), cookieName ) ).findFirst();
    }

    @Override public String contentType() {
        return mediaType.toString();
    }

    @Override public MediaType mediaType() {
        return mediaType;
    }

    @Override public Optional<Charset> charset() {
        return mediaType.charset().toJavaUtil();
    }

    @Override public Object rawRequest() {
        return rawRequest;
    }
}
