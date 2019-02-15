package rip.deadcode.abukuma3.value;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import org.eclipse.jetty.server.Request;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.internal.Unsafe;
import rip.deadcode.abukuma3.internal.utils.Uncheck;

import javax.annotation.Nullable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Set;

import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


// TODO Should be integrated into the AbuHeader
public final class AbuRequestHeader {

    private final AbuExecutionContext context;
    private final Request jettyRequest;

    public AbuRequestHeader( AbuExecutionContext context, Request jettyRequest ) {
        this.context = context;
        this.jettyRequest = jettyRequest;
    }

    public AbuExecutionContext context() {
        return context;
    }

    public String method() {
        return jettyRequest.getMethod();
    }

    /**
     * Returns the URL reconstructed by jetty.
     *
     * @return URL of the request
     * @see Request#getRequestURL()
     */
    public URL url() {
        return Uncheck.uncheck( () -> new URL( jettyRequest.getRequestURL().toString() ) );
    }

    /**
     * Returns the URL {@link String} sent from the client.
     * It is without query parameters.
     *
     * @return URL String
     * @see Request#getRequestURI()
     */
    public String requestUri() {
        return jettyRequest.getRequestURI();
    }

    @Nullable
    public String getValue( String headerName ) {
        return jettyRequest.getHeader( headerName );
    }

    public Set<String> getValues( String headerName ) {
        return ImmutableSet.copyOf( Iterators.forEnumeration( jettyRequest.getHeaders( headerName ) ) );
    }

    public Optional<String> mayGet( String headerName ) {
        return Optional.ofNullable( jettyRequest.getHeader( headerName ) );
    }

    public String contentType() {
        return jettyRequest.getContentType();
    }

    public Optional<Charset> charset() {
        // Not sure which is better
        return possibly( () -> Charset.forName( jettyRequest.getCharacterEncoding() ) ).asOptional();
    }

    @Unsafe
    public Request jettyRequest() {
        return jettyRequest;
    }
}
