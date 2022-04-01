package rip.deadcode.abukuma3.value;

import java.util.Optional;
import java.util.OptionalInt;


public interface Cookie {

    public String name();

    public Cookie name( String name );

    /**
     * The value of this cookie.
     */
    public String value();

    /**
     * @see #value()
     */
    public Cookie value( String value );

    /**
     * Expiry time of this cookie(seconds).
     * Returns empty optional for a session cookie.
     * This method may also return `-1` for a session cookie,
     * since Java Servlet cannot distinguish a null header form a minus value.
     *
     * @return `Max-Age` attribute.
     */
    public OptionalInt maxAge();

    /**
     * @see #maxAge()
     */
    public Cookie maxAge( Integer maxAge );

    public Optional<String> domain();

    public Cookie domain( String domain );

    public Optional<String> path();

    public Cookie path( String path );

    /**
     * Returns true only if the `Secure` attribute is set. Otherwise false.
     *
     * @return `Secure` attribute.
     */
    public boolean secure();

    /**
     * @see #secure()
     */
    public Cookie secure( boolean secure );

    /**
     * Returns true only if the `HttpOnly` attribute is set. Otherwise false.
     *
     * @return `HttpOnly` attribute.
     */
    public boolean httpOnly();

    /**
     * @see #httpOnly()
     */
    public Cookie httpOnly( boolean httpOnly );

    /**
     * `SameSite` attribute. The value should be either `strict` or `lax`.
     * The value is assumed case-insensitive, but this method will return the value as-is.
     *
     * @return `SameSite` attribute.
     * @see <a href="https://tools.ietf.org/html/draft-ietf-httpbis-rfc6265bis-02">https://tools.ietf.org/html/draft-ietf-httpbis-rfc6265bis-02</a>
     */
    public Optional<String> sameSite();

    /**
     * @see #sameSite()
     */
    public Cookie sameSite( String sameSite );

    // TODO
//    public PersistentMap<String, String> nonStandardValues();
//    public Cookie nonStandardValues( Map<String, String> nonStandardValues );
}
