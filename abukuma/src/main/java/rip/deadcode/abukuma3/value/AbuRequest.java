package rip.deadcode.abukuma3.value;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public final class AbuRequest {

    private final AbuExecutionContext context;
    private final AbuRequestHeader header;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;
    private final HttpServletResponse servletResponse;
    private final Map<String, String> pathParams;

    public AbuRequest(
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
    public <T> T body( Class<T> cls ) {
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

    public AbuExecutionContext context() {
        return context;
    }

    public AbuRequestHeader header() {
        return header;
    }

    public Map<String, String> pathParams() {
        return pathParams;
    }

    // TODO use original implementation for getting single value
    public Multimap<String, String> queryParams() {
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
    public Request jettyRequest() {
        return jettyRequest;
    }

    @Unsafe
    public HttpServletRequest servletRequest() {
        return servletRequest;
    }

    @Unsafe
    public HttpServletResponse servletResponse() {
        return servletResponse;
    }
}
