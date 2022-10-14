package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapMap;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapMultimap;


public final class RequestImpl<T> implements Request<T> {

    private final T body;
    private final RequestHeader header;
    private final PersistentMap<String, String> pathParams;
    private final PersistentMultimap<String, String> queryParams;
    @Nullable private final Object rawRequest;
    @Nullable private final Object rawResponse;

    public RequestImpl(
            T body,
            RequestHeader header,
            Map<String, String> pathParams,
            Multimap<String, String> queryParams
    ) {
        this.body = body;
        this.header = header;
        this.pathParams = wrapMap( pathParams );
        this.queryParams = wrapMultimap( queryParams );
        this.rawRequest = null;
        this.rawResponse = null;
    }

    public RequestImpl(
            T body,
            RequestHeader header,
            Map<String, String> pathParams,
            Multimap<String, String> queryParams,
            Object rawRequest,
            Object rawResponse
    ) {
        this.body = body;
        this.header = header;
        this.pathParams = wrapMap( pathParams );
        this.queryParams = wrapMultimap( queryParams );
        this.rawRequest = rawRequest;
        this.rawResponse = rawResponse;
    }

    @Override public T body() {
        return body;
    }

    @Override public RequestHeader header() {
        return header;
    }

    @Override public Optional<String> pathParam( String key ) {
        return pathParams.mayGet( key );
    }

    @Override public PersistentMap<String, String> pathParams() {
        return pathParams;
    }

    @Override public Optional<String> queryParam( String key ) {
        return queryParams.mayGet( key );
    }

    @Override public PersistentMultimap<String, String> queryParams() {
        return queryParams;
    }

    @Override public Object rawRequest() {
        if ( rawRequest == null ) {
            throw new UnsupportedOperationException();
        }

        return rawRequest;
    }

    @Override public Object rawResponse() {
        if ( rawResponse == null ) {
            throw new UnsupportedOperationException();
        }

        return rawResponse;
    }
}
