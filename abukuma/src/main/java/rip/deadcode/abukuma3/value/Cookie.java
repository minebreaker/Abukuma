package rip.deadcode.abukuma3.value;

import java.util.Optional;
import java.util.OptionalInt;


public interface Cookie {

    public String name();

    public Cookie name( String name );

    public String value();

    public Cookie value( String value );

    // TODO damn IE compatibility
//    /**
//     * `Expires` attribute of this cookie.
//     * You should use {@link #maxAge()} unless you are targeting old Internet Explorer 8.
//     * @return `Expires` attribute.
//     */
//    public Optional<LocalDateTime> expires();

//    public Cookie expires( LocalDateTime expires );

    /**
     * Expiry time of this cookie(seconds).
     * Returns empty optional for the session cookie.
     * @return `Max-Age` attribute.
     */
    public OptionalInt maxAge();

    /**
     * `0` or minus values will be sent as-is,
     * which means the cookie will expire immediately.
     * @param maxAge
     * @return
     */
    public Cookie maxAge( int maxAge );

    // TODO we need this for the session cookie
//    public Cookie withoutMaxAge();

    public Optional<String> domain();

    public Cookie domain( String domain );

    public Optional<String> path();

    public Cookie path( String path );

    /**
     * Returns true only if the `Secure` attribute is set. Otherwise false.
     * @return `Secure` attribute.
     */
    public boolean secure();

    public Cookie secure( boolean secure );

    /**
     * Returns true only if the `HttpOnly` attribute is set. Otherwise false.
     * @return `HttpOnly` attribute.
     */
    public boolean httpOnly();

    public Cookie httpOnly( boolean httpOnly );

    /**
     * `SameSite` attribute. The value should be either `strict` or `lax`.
     * The value is assumed case-insensitive, but this method will return the value as-is.
     *
     * @return `SameSite` attribute.
     * @see <a href="https://tools.ietf.org/html/draft-ietf-httpbis-rfc6265bis-02">https://tools.ietf.org/html/draft-ietf-httpbis-rfc6265bis-02</a>
     */
    public Optional<String> sameSite();

    public Cookie sameSite( String sameSite );
}
