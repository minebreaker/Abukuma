package rip.deadcode.abukuma3.service;

import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ContextImpl implements Context {

    private Request request;
    private Future<Response> response;

    public ContextImpl(Request request) {
        this(request, null);
    }

    ContextImpl(Request request, @Nullable Future<Response> response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public Future<Response> getResponse() {
        checkNotNull(response, "Future<Response> is null");
        return response;
    }

    @Override
    public Context put(String key, Object value) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
