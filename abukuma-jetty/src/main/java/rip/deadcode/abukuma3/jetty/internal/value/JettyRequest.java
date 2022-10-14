package rip.deadcode.abukuma3.jetty.internal.value;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import jakarta.servlet.http.HttpServletResponse;
import rip.deadcode.abukuma3.Unsafe;
import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.net.URI;
import java.util.Optional;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.mayFirst;


public final class JettyRequest<T> implements Request<T> {

    private final T body;
    private final RequestHeader header;
    private final org.eclipse.jetty.server.Request jettyRequest;
    private final HttpServletResponse servletResponse;
    private final PersistentMap<String, String> pathParams;

    public JettyRequest(
            T body,
            RequestHeader header,
            org.eclipse.jetty.server.Request jettyRequest,
            HttpServletResponse servletResponse,
            PersistentMap<String, String> pathParams ) {
        this.body = body;
        this.header = header;
        this.jettyRequest = jettyRequest;
        this.servletResponse = servletResponse;
        this.pathParams = pathParams;
    }

    @Override public T body() {
        return body;
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

    @Override public PersistentMap<String, String> pathParams() {
        return pathParams;
    }

    @Override public Optional<String> queryParam( String key ) {
        return mayFirst( jettyRequest.getQueryParameters().getValues( key ) );
    }

    @Override public PersistentMultimap<String, String> queryParams() {
        // TODO: use PersistentMultimap directly
        Multimap<String, String> params =
                jettyRequest.getQueryParameters().entrySet().stream()
                            .collect(
                                    ArrayListMultimap::create,
                                    ( map, e ) -> map.putAll( e.getKey(), e.getValue() ),
                                    Multimap::putAll
                            );
        return PersistentCollections.wrapMultimap( params );
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
