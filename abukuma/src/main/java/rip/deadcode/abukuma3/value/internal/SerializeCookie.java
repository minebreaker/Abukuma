package rip.deadcode.abukuma3.value.internal;

import com.google.common.net.UrlEscapers;
import rip.deadcode.abukuma3.value.Cookie;


public final class SerializeCookie {

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
}
