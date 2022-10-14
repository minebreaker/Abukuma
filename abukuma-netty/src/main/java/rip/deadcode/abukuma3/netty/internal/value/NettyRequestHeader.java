package rip.deadcode.abukuma3.netty.internal.value;

import com.google.common.net.MediaType;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.netty.internal.NettyHandler;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public class NettyRequestHeader implements RequestHeader {

    private final PersistentList<String> urlPaths;
    private final NettyHandler.RequestAndContent request;

    public NettyRequestHeader( PersistentList<String> urlPaths, NettyHandler.RequestAndContent request ) {
        this.urlPaths = urlPaths;
        this.request = request;
    }

    @Override
    public String method() {
        return request.request.method().name();
    }

    @Override
    public URI url() {
        return uncheck( () -> new URI( request.request.uri() ) );
    }

    @Override
    public String urlString() {
        return request.request.uri();
    }

    @Override public PersistentList<String> urlPaths() {
        return urlPaths;
    }

    @Nullable
    @Override
    public String getValue( String headerName ) {
        return null;
    }

    @Override
    public PersistentList<String> getValues( String headerName ) {
        return null;
    }

    @Override
    public Optional<String> mayGet( String headerName ) {
        return Optional.empty();
    }

    @Override
    public PersistentList<Cookie> cookie() {
        return null;
    }

    @Override
    public Optional<Cookie> cookie( String cookieName ) {
        return Optional.empty();
    }

    @Override
    public String contentType() {
        return null;
    }

    @Override public MediaType mediaType() {
        return null;
    }

    @Override
    public Optional<Charset> charset() {
        return Optional.empty();
    }

    @Override
    public NettyHandler.RequestAndContent rawRequest() {
        return request;
    }
}
