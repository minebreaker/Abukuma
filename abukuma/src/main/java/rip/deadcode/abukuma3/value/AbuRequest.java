package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Map;


public interface AbuRequest {

    public <T> T body( Class<T> cls );

    public AbuExecutionContext context();

    public String method();

    public URL url();

    public String requestUri();

    public AbuRequestHeader header();

    public Map<String, String> pathParams();

    public Multimap<String, String> queryParams();

    @Unsafe
    public Request jettyRequest();

    @Unsafe
    public HttpServletRequest servletRequest();

    @Unsafe
    public HttpServletResponse servletResponse();
}
