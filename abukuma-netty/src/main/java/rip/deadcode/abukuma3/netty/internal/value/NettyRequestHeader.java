package rip.deadcode.abukuma3.netty.internal.value;

import com.google.common.net.MediaType;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.netty.internal.NettyHandler;
import rip.deadcode.abukuma3.value.RequestHeader;
import rip.deadcode.abukuma3.value.Cookie;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


public class NettyRequestHeader implements RequestHeader {

    private final ExecutionContext context;
    private final NettyHandler.RequestAndContent request;

    public NettyRequestHeader( ExecutionContext context, NettyHandler.RequestAndContent request ) {
        this.context = context;
        this.request = request;
    }

    @Override
    public ExecutionContext context() {
        return context;
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

    @Nullable
    @Override
    public String getValue( String headerName ) {
        return null;
    }

    @Override
    public Set<String> getValues( String headerName ) {
        return null;
    }

    @Override
    public Optional<String> mayGet( String headerName ) {
        return Optional.empty();
    }

    @Override
    public List<Cookie> cookie() {
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
