package rip.deadcode.abukuma3.value;


import com.google.common.net.MediaType;
import rip.deadcode.abukuma3.Unsafe;
import rip.deadcode.abukuma3.collection.PersistentList;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;


public interface RequestHeader {

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

    /**
     * Returns the path strings split by '/'.
     * Trailing slashes are represented as empty strings.
     * If the path is root (just '/'), the list contains exactly one empty string.
     *
     * <table>
     *     <tr><th>The path in the request</th><th>The returned list</th></tr>
     *     <tr><td><code>/</code></td><td><code>[""]</code></td></tr>
     *     <tr><td><code>/foo/bar</code></td><td><code>["foo", "bar"]</code></td></tr>
     *     <tr><td><code>/foo///bar</code></td><td><code>["foo", "", "", "bar"]</code></td></tr>
     *     <tr><td><code>*</code></td><td><code>["*"]</code></td></tr>
     * </table>
     */
    public PersistentList<String> urlPaths();

    /**
     * Note that header name is case insensitive.
     * {@code getValue("X-HeaderName")} is equivalent to {@code getValue("x-headername")}.
     */
    @Nullable
    public String getValue( String headerName );

    public PersistentList<String> getValues( String headerName );

    public Optional<String> mayGet( String headerName );

    // TODO: set or list?
    public PersistentList<Cookie> cookie();

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
     *
     * @return String The content type header
     */
    @Unsafe
    public MediaType mediaType();

    /**
     * The charset of the request.
     * The value can be, typically but not necessarily, the charset specified in the "Content-Type" header.
     * The server implementation is allowed to choose the way to detect it.
     *
     * @return The charset of the request
     */
    public Optional<Charset> charset();

//     TODO: guava HostSpecifier?
//    public Optional<String> host();
//
//    public String remoteAddress();

    @Unsafe
    public Object rawRequest();
}
