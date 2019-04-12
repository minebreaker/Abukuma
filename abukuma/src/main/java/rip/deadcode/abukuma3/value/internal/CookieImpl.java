package rip.deadcode.abukuma3.value.internal;

import com.google.common.net.UrlEscapers;
import rip.deadcode.abukuma3.value.Cookie;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.OptionalInt;

import static com.google.common.base.Preconditions.checkNotNull;


public final class CookieImpl implements Cookie {

    @Nonnull private String name;
    @Nonnull private String value;
    private int maxAge;
    @Nullable private String domain;
    @Nullable private String path;
    private boolean secure;
    private boolean httpOnly;
    @Nullable private String sameSite;

    public CookieImpl( String name, String value ) {
        this.name = checkNotNull( name );
        this.value = checkNotNull( value );
        this.maxAge = -1;  // FIXME
    }

    public CookieImpl(
            String name,
            String value,
            int maxAge,
            String domain,
            String path,
            boolean secure,
            boolean httpOnly,
            String sameSite
    ) {
        this.name = checkNotNull( name );
        this.value = checkNotNull( value );
        this.maxAge = maxAge;  // TODO default to null
        this.domain = domain;
        this.path = path;  // TODO default to "/"?
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.sameSite = sameSite;
    }

    private CookieImpl copy() {
        return new CookieImpl( name, value, maxAge, domain, path, secure, httpOnly, sameSite );
    }

    public static CookieImpl fromServletCookie( javax.servlet.http.Cookie cookie ) {
        return new CookieImpl(
                cookie.getName(),
                cookie.getValue(),
                cookie.getMaxAge(),
                cookie.getDomain(),
                cookie.getPath(),
                cookie.getSecure(),
                cookie.isHttpOnly(),
                null
        );
    }

    public static String serialize( Cookie cookie ) {
        String name = cookie.name();  // TODO validate
        String value = UrlEscapers.urlFormParameterEscaper().escape( cookie.value() );
        String maxAge = cookie.maxAge().isPresent() ? "; Max-Age=" + cookie.maxAge().getAsInt() : "";
        String domain = cookie.domain().isPresent() ? "; Domain=" + cookie.domain().get() : "";
        String path = cookie.path().isPresent() ? "; Path=" + cookie.path().get() : "";
        String secure = cookie.secure() ? "; Secure" : "";
        String httpOnly = cookie.httpOnly() ? "; HttpOnly" : "";
        String sameSite = cookie.sameSite().isPresent() ? "; SameSite=" + cookie.sameSite() : "";
        return name + "=" + value + maxAge + domain + path + secure + httpOnly + sameSite;
    }

    @Override public String name() {
        return name;
    }

    @Override public Cookie name( String name ) {
        CookieImpl c = copy();
        c.name = name;
        return c;
    }

    @Override public String value() {
        return value;
    }

    @Override public Cookie value( String value ) {
        CookieImpl c = copy();
        c.value = value;
        return c;
    }

    @Override public OptionalInt maxAge() {
        return OptionalInt.of( maxAge );
    }

    @Override public Cookie maxAge( int maxAge ) {
        CookieImpl c = copy();
        c.maxAge = maxAge;
        return c;
    }

    @Override public Optional<String> domain() {
        return Optional.ofNullable( domain );
    }

    @Override public Cookie domain( String domain ) {
        CookieImpl c = copy();
        c.domain = domain;
        return c;
    }

    @Override public Optional<String> path() {
        return Optional.ofNullable( path );
    }

    @Override public Cookie path( String path ) {
        CookieImpl c = copy();
        c.path = path;
        return c;
    }

    @Override public boolean secure() {
        return secure;
    }

    @Override public Cookie secure( boolean secure ) {
        CookieImpl c = copy();
        c.secure = secure;
        return c;
    }

    @Override public boolean httpOnly() {
        return httpOnly;
    }

    @Override public Cookie httpOnly( boolean httpOnly ) {
        CookieImpl c = copy();
        c.httpOnly = httpOnly;
        return c;
    }

    @Override public Optional<String> sameSite() {
        return Optional.ofNullable( sameSite );
    }

    @Override public Cookie sameSite( String sameSite ) {
        CookieImpl c = copy();
        c.sameSite = sameSite;
        return c;
    }
}
