package rip.deadcode.abukuma3.service;

import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;

import java.util.Collections;
import java.util.Map;

public interface Context {

    public Request getRequest();

    public Future<Response> getResponse();

    public default Map<String, String> getPathParam() {
        return Collections.emptyMap();
    }

    public Context put(String key, Object value);

}
