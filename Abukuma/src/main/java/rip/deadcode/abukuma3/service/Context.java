package rip.deadcode.abukuma3.service;

import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public interface Context {

    public Request getRequest();

    public Context response(Future<Response> response);

    public Future<Response> getResponse();

    public Context pathParam(Map<String, String> pathParam);

    public default Map<String, String> getPathParam() {
        return Collections.emptyMap();
    }

    public Context contextualPath(String contextualPath);

    /**
     * Path used to route matching, which can differ from real request path.
     *
     * @return Contextual path.
     */
    public String getContextualPath();

    public Context put(String key, Object value);

    public Optional<Object> get(String key);

}
