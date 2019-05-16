package rip.deadcode.abukuma3.jetty.internal.value;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.utils.Uncheck;
import rip.deadcode.abukuma3.value.AbuRequestHeader;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.internal.CookieImpl;

import javax.annotation.Nullable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


// TODO Should be integrated into the AbuHeader
public final class JettyRequestHeader implements AbuRequestHeader {

    private final AbuExecutionContext context;
    private final Request jettyRequest;

    public JettyRequestHeader( AbuExecutionContext context, Request jettyRequest ) {
        this.context = context;
        this.jettyRequest = jettyRequest;
    }

    @Override public AbuExecutionContext context() {
        return context;
    }

    @Override public String method() {
        return jettyRequest.getMethod();
    }

    @Override public URL url() {
        return Uncheck.uncheck( () -> new URL( jettyRequest.getRequestURL().toString() ) );
    }

    @Override public String requestUri() {
        return jettyRequest.getRequestURI();
    }

    @Nullable @Override public String getValue( String headerName ) {
        return jettyRequest.getHeader( headerName );
    }

    @Override public Set<String> getValues( String headerName ) {
        return ImmutableSet.copyOf( Iterators.forEnumeration( jettyRequest.getHeaders( headerName ) ) );
    }

    @Override public Optional<String> mayGet( String headerName ) {
        return Optional.ofNullable( jettyRequest.getHeader( headerName ) );
    }

    private static CookieImpl fromServletCookie( javax.servlet.http.Cookie cookie ) {
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

    @Override public List<Cookie> cookie() {
        if ( jettyRequest.getCookies() == null ) {
            return ImmutableList.of();
        }

        return Arrays.stream( jettyRequest.getCookies() )
                     .map( JettyRequestHeader::fromServletCookie )
                     .collect( Collectors.toList() );
    }

    @Override public Optional<Cookie> cookie( String cookieName ) {
        return Arrays.stream( jettyRequest.getCookies() )
                     .filter( c -> c.getName().equals( cookieName ) )
                     .findAny()
                     .map( JettyRequestHeader::fromServletCookie );
    }

    @Override public String contentType() {
        return jettyRequest.getContentType();
    }

    @Override public Optional<Charset> charset() {
        return possibly( () -> Charset.forName( jettyRequest.getCharacterEncoding() ) ).asOptional();
    }

    @Override public Request rawRequest() {
        return jettyRequest;
    }
}