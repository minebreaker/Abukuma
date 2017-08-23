package rip.deadcode.abukuma3.service;

import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

import java.util.Collections;
import java.util.Map;

public interface Context {

    public Request getRequest();

    public Future<Response> getResponse();

    public default Context response(Future<Response> response) {
        return new ContextImpl(this.getRequest(), response);
    }

    /**
     * Response is not copied.
     *
     * @param pathParam Path parameters
     * @return Copy with given pathParam.
     */
    public default Context pathParam(Map<String, String> pathParam) {
        return new ContextImpl(this.getRequest(), null, pathParam);
    }

    public default Map<String, String> getPathParam() {
        return Collections.emptyMap();
    }

    public default Context put(String key, Object value) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public default Object get(String key) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
