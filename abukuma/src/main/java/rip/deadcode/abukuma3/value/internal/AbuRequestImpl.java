package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.mayFirst;


public final class AbuRequestImpl implements AbuRequest {

    private final AbuExecutionContext context;
    private final AbuRequestHeader header;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;
    private final HttpServletResponse servletResponse;
    private final Map<String, String> pathParams;

    public AbuRequestImpl(
            AbuExecutionContext context,
            AbuRequestHeader header,
            Request jettyRequest,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            Map<String, String> pathParams ) {
        this.context = context;
        this.header = header;
        this.jettyRequest = jettyRequest;
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.pathParams = pathParams;
    }

    @SuppressWarnings( "unchecked" )  // checked by Class.isInstance(Object)
    @Override public <T> T body( Class<T> cls ) {
        try ( InputStream is = jettyRequest.getInputStream() ) {
            Object result = context.parserChain().parse( cls, is, header );
            checkNotNull( result, "Could not find an appropriate parser for the type '%s'.", cls );
            checkState(
                    cls.isInstance( result ),
                    "Illegal instance '%s' of type '%s' was returned by the parser for the request '%s'. This may be caused by a bug of the parsers.",
                    result, result.getClass(), cls
            );
            return (T) result;

        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }

    @Override public AbuExecutionContext context() {
        return context;
    }

    @Override public String method() {
        return header.method();
    }

    @Override public URL url() {
        return header.url();
    }

    @Override public String requestUri() {
        return header.requestUri();
    }

    @Override public AbuRequestHeader header() {
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

    @Unsafe
    @Override
    public Request jettyRequest() {
        return jettyRequest;
    }

    @Unsafe
    @Override
    public HttpServletRequest servletRequest() {
        return servletRequest;
    }

    @Unsafe
    @Override
    public HttpServletResponse servletResponse() {
        return servletResponse;
    }
}
