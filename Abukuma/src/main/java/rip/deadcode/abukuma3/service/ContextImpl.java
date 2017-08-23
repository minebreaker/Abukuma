package rip.deadcode.abukuma3.service;

import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ContextImpl implements Context {

    private Request request;
    private Future<Response> response;
    private Map<String, String> pathParam;

    public ContextImpl(Request request) {
        this(request, null);
    }

    ContextImpl(Request request, @Nullable Future<Response> response) {
        this(request, response, null);
    }

    ContextImpl(Request request, @Nullable Future<Response> response, @Nullable Map<String, String> pathParam) {
        this.request = request;
        this.response = response;
        this.pathParam = pathParam;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public Future<Response> getResponse() {
        checkNotNull(response, "Response has not been set yet.");
        return response;
    }

    public Map<String, String> getPathParam() {
        return pathParam == null ? Collections.emptyMap() : pathParam;
    }

}
