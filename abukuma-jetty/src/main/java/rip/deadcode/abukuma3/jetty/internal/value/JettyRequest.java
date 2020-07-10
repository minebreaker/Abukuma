package rip.deadcode.abukuma3.jetty.internal.value;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Unsafe;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.mayFirst;


public final class JettyRequest implements Request {

    private final ExecutionContext context;
    private final RequestHeader header;
    private final org.eclipse.jetty.server.Request jettyRequest;
    private final HttpServletResponse servletResponse;
    private final Map<String, String> pathParams;

    public JettyRequest(
            ExecutionContext context,
            RequestHeader header,
            org.eclipse.jetty.server.Request jettyRequest,
            HttpServletResponse servletResponse,
            Map<String, String> pathParams ) {
        this.context = context;
        this.header = header;
        this.jettyRequest = jettyRequest;
        this.servletResponse = servletResponse;
        this.pathParams = pathParams;
    }

    @SuppressWarnings( "unchecked" )  // checked by Class.isInstance(Object)
    @Override public <T> T body( Class<T> cls ) {
        try ( InputStream is = jettyRequest.getInputStream() ) {
            Object result = context.parser().parse( cls, is, header );
            checkNotNull( result, "Could not find an appropriate parser for the type '%s'.", cls );
            checkState(
                    cls.isInstance( result ),
                    "Illegal instance '%s' of type '%s' was returned by the parser for the request '%s'. This may be caused by a bug of the parsers.",
                    result,
                    result.getClass(),
                    cls
            );
            return (T) result;

        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }

    @Override public String method() {
        return header.method();
    }

    @Override public URI url() {
        return header.url();
    }

    @Override public String urlString() {
        return header.urlString();
    }

    @Override public RequestHeader header() {
        return header;
    }

    @Override public Optional<String> pathParam( String key ) {
        return Optional.ofNullable( pathParams.get( key ) );
    }

    @Override public Map<String, String> pathParams() {
        return pathParams;
    }

    @Override public Optional<String> queryParam( String key ) {
        return mayFirst( jettyRequest.getQueryParameters().getValues( key ) );
    }

    @Override public Multimap<String, String> queryParams() {
        Multimap<String, String> params =
                jettyRequest.getQueryParameters().entrySet().stream()
                            .collect(
                                    ArrayListMultimap::create,
                                    ( map, e ) -> map.putAll( e.getKey(), e.getValue() ),
                                    Multimap::putAll
                            );
        return ImmutableListMultimap.copyOf( params );
    }

    @Override public Optional<String> host() {
        return Optional.ofNullable( jettyRequest.getHeader( HttpHeaders.HOST ) );
    }

    @Override public String remoteAddress() {
        return jettyRequest.getRemoteAddr();
    }

    @Unsafe
    @Override
    public org.eclipse.jetty.server.Request rawRequest() {
        return jettyRequest;
    }

    @Unsafe
    @Override
    public HttpServletResponse rawResponse() {
        return servletResponse;
    }
}
