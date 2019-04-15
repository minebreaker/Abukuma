package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.Unsafe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Map;
import java.util.Optional;


public interface AbuRequest {

    public <T> T body( Class<T> cls );

    public AbuExecutionContext context();

    public String method();

    public URL url();

    public String requestUri();

    // TODO header can have multiple values, but only the last one should be used?
    public AbuRequestHeader header();

    public Optional<String> pathParam( String key );

    public Map<String, String> pathParams();

    public Optional<String> queryParam( String key );

    // TODO Query param can have multiple values?
    public Multimap<String, String> queryParams();

    @Unsafe
    public Request jettyRequest();

    @Unsafe
    public HttpServletRequest servletRequest();

    @Unsafe
    public HttpServletResponse servletResponse();
}
