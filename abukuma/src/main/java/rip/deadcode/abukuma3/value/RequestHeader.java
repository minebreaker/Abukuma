package rip.deadcode.abukuma3.value;


import com.google.common.net.MediaType;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.Unsafe;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface RequestHeader {

    public ExecutionContext context();

    public String method();

    /**
     * Returns the URL reconstructed by server implementation.
     *
     * @return URL of the request
     */
    public URI url();

    /**
     * Returns the URL {@link String} sent from the client.
     * This is the `Request-URI` specified by the RFC 2616§5.1.2.
     *
     * @return URL String
     */
    public String urlString();

    @Nullable
    public String getValue( String headerName );

    public Set<String> getValues( String headerName );

    public Optional<String> mayGet( String headerName );

    public List<Cookie> cookie();

    public Optional<Cookie> cookie( String cookieName );

    /**
     * The "Content-Type" header.
     * The value is without any parameters.
     * For example, if the value sent by client is
     * "application/json; charset=utf-8",returned value is "application/json".
     * <!-- If you need parameters, use the {@link #mediaType} method. -->
     * <!-- If you need a raw value, use the {@code getValue("content-type")}. -->
     * If you need a charset in the header, use {@link #charset()}
     *
     * @return The content type header
     */
    public String contentType();

    /**
     * The "Content-Type" header.
     * @return String The content type header
     */
    @Unsafe
    public MediaType mediaType();

    /**
     * The charset of the request.
     * The value can be, but not necessarily, the charset specified in the "Content-Type" header.
     * The server implementation is allowed to choose the way to detect it.
     * @return The charset of the request
     */
    public Optional<Charset> charset();

    @Unsafe
    public Object rawRequest();
}
