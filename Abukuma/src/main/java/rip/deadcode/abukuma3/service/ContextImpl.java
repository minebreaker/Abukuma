package rip.deadcode.abukuma3.service;

import com.google.common.collect.ImmutableMap;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ContextImpl implements Context {

    private Request request;
    @Nullable
    private Future<Response> response;
    private Map<String, String> pathParam;
    private String contextualPath;
    private Map<String, Object> contextualInfo;

    public ContextImpl(Request request) {
        this(request, null, null, "/", null);
    }

    private ContextImpl(ContextImpl that) {
        this.request = that.request;
        this.response = that.response;
        this.pathParam = that.pathParam;
        this.contextualPath = that.contextualPath;
        this.contextualInfo = that.contextualInfo;
    }

    private ContextImpl(
            Request request,
            @Nullable Future<Response> response,
            @Nullable Map<String, String> pathParam,
            String contextualPath,
            @Nullable Map<String, Object> contextualInfo) {
        this.request = request;
        this.response = response;
        this.pathParam = pathParam != null ? pathParam : Collections.emptyMap();
        this.contextualPath = contextualPath;
        this.contextualInfo = contextualInfo != null ? contextualInfo : Collections.emptyMap();
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public Context response(Future<Response> response) {
        checkNotNull(response);
        ContextImpl newInstance = new ContextImpl(this);
        newInstance.response = response;
        return newInstance;
    }

    @Override
    public Context pathParam(Map<String, String> pathParam) {
        checkNotNull(pathParam);
        ContextImpl newInstance = new ContextImpl(this);
        newInstance.pathParam = pathParam;
        return newInstance;
    }

    @Override
    public Future<Response> getResponse() {
        checkNotNull(response, "Response has not been set yet.");
        return response;
    }

    @Override
    public Map<String, String> getPathParam() {
        return pathParam == null ? Collections.emptyMap() : pathParam;
    }

    @Override
    public Context contextualPath(String contextualPath) {
        checkNotNull(pathParam);
        ContextImpl newInstance = new ContextImpl(this);
        newInstance.contextualPath = contextualPath;
        return newInstance;
    }

    @Override
    public String getContextualPath() {
        checkNotNull(contextualPath);
        return contextualPath;
    }

    @Override
    public Context put(String key, Object value) {
        checkNotNull(key);
        checkNotNull(value);
        ContextImpl newInstance = new ContextImpl(this);
        newInstance.contextualInfo = ImmutableMap.<String, Object>builder()
                .putAll(contextualInfo)
                .put(key, value)
                .build();
        return newInstance;
    }

    @Override
    public Optional<Object> get(String key) {
        checkNotNull(key);
        return Optional.ofNullable(contextualInfo.get(key));
    }

}
