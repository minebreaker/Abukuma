package rip.deadcode.abukuma3.value;


import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.Unsafe;

import javax.annotation.Nullable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface AbuRequestHeader {

    public AbuExecutionContext context();

    public String method();

    /**
     * Returns the URL reconstructed by jetty.
     *
     * @return URL of the request
     * @see Request#getRequestURL()
     */
    public URL url();

    /**
     * Returns the URL {@link String} sent from the client.
     * It is without query parameters.
     *
     * @return URL String
     * @see Request#getRequestURI()
     */
    public String requestUri();

    @Nullable
    public String getValue( String headerName );

    public Set<String> getValues( String headerName );

    public Optional<String> mayGet( String headerName );

    public List<Cookie> cookie();

    public Optional<Cookie> cookie( String cookieName );

    public String contentType();

    public Optional<Charset> charset();

    @Unsafe
    public Object rawRequest();
}
