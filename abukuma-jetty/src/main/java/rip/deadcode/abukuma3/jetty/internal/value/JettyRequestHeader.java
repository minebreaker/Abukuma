package rip.deadcode.abukuma3.jetty.internal.value;

import com.google.common.collect.Iterators;
import com.google.common.net.MediaType;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.internal.CookieImpl;
import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.RequestHeader;

import javax.annotation.Nullable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.collection.PersistentCollectors.toPersistentList;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;
import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


// TODO Should be integrated into the AbuHeader
public final class JettyRequestHeader implements RequestHeader {

    private final ExecutionContext context;
    private final PersistentList<String> urlPaths;
    private final Request jettyRequest;

    public JettyRequestHeader( ExecutionContext context, PersistentList<String> urlPaths, Request jettyRequest ) {
        this.context = context;
        this.urlPaths = urlPaths;
        this.jettyRequest = jettyRequest;
    }

    @Override public ExecutionContext context() {
        return context;
    }

    @Override public String method() {
        return jettyRequest.getMethod();
    }

    @Override public URI url() {
        return uncheck( () -> new URI(
                jettyRequest.getScheme(),
                null,
                jettyRequest.getServerName(),
                jettyRequest.getServerPort(),
                jettyRequest.getRequestURI(),
                jettyRequest.getQueryString(),
                null
        ) );
    }

    @Override public String urlString() {
        return jettyRequest.getRequestURI();
    }

    @Override public PersistentList<String> urlPaths() {
        return urlPaths;
    }

    @Nullable @Override public String getValue( String headerName ) {
        return jettyRequest.getHeader( headerName );
    }

    @Override public Set<String> getValues( String headerName ) {
        return PersistentCollections.wrapSet( Iterators.forEnumeration( jettyRequest.getHeaders( headerName ) ) );
    }

    @Override public Optional<String> mayGet( String headerName ) {
        return Optional.ofNullable( jettyRequest.getHeader( headerName ) );
    }

    private static CookieImpl fromServletCookie( jakarta.servlet.http.Cookie cookie ) {
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
            return createList();
        }

        return Arrays.stream( jettyRequest.getCookies() )
                     .map( JettyRequestHeader::fromServletCookie )
                     .collect( toPersistentList() );
    }

    @Override public Optional<Cookie> cookie( String cookieName ) {
        return Arrays.stream( jettyRequest.getCookies() )
                     .filter( c -> c.getName().equals( cookieName ) )
                     .findAny()
                     .map( JettyRequestHeader::fromServletCookie );
    }

    @Override public String contentType() {
        return mediaType().withoutParameters().toString();
    }

    @Override public MediaType mediaType() {
        return MediaType.parse( jettyRequest.getContentType() );
    }

    @Override public Optional<Charset> charset() {
        return possibly( () -> Charset.forName( jettyRequest.getCharacterEncoding() ) ).asOptional();
    }

    @Override public Request rawRequest() {
        return jettyRequest;
    }
}
