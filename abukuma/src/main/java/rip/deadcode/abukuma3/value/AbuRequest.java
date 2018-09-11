package rip.deadcode.abukuma3.value;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;
import rip.deadcode.abukuma3.parser.AbuParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.akashi.util.Uncheck.tryUncheck;

public final class AbuRequest {

    private final ExecutionContext context;
    private final AbuRequestHeader header;
    private final Request jettyRequest;
    private final HttpServletRequest servletRequest;
    private final HttpServletResponse servletResponse;
    private final Map<String, String> pathParams;

    public AbuRequest(
            ExecutionContext context,
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

    @SuppressWarnings( "unchecked" )
    public <T> T body( Class<T> cls ) {
        AbuParser<?> parser = context.getParsers().get( cls );
        checkNotNull( parser, "Could not find an appropriate parser for the type '%s'.", cls );
        return (T) parser.parse( tryUncheck( () -> jettyRequest.getInputStream() ), header );
    }

    public ExecutionContext context() {
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
