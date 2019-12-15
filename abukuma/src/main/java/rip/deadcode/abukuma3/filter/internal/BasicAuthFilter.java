package rip.deadcode.abukuma3.filter.internal;

import com.google.common.base.Splitter;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.filter.AbuFilter;
import rip.deadcode.abukuma3.filter.AuthRequest;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


public class BasicAuthFilter implements AbuFilter {

    private static final AbuResponse unauthorized = AbuResponses
            .create( "401 Unauthorized" )
            .status( 401 );  // TODO

    private static final Splitter splitter = Splitter.on( ":" ).limit( 2 );

    private final Predicate<AuthRequest> accepts;
    private final Function<AbuRequest, String> calcRealm;

    public BasicAuthFilter( Predicate<AuthRequest> accepts ) {
        this( accepts, req -> "default" );
    }

    public BasicAuthFilter( Predicate<AuthRequest> accepts, Function<AbuRequest, String> calcRealm ) {
        this.accepts = accepts;
        this.calcRealm = calcRealm;
    }

    private static final class AuthRequestImpl implements AuthRequest {

        private final String userId;
        private final String password;
        private final AbuRequest request;

        private AuthRequestImpl( String userId, String password, AbuRequest request ) {
            this.userId = userId;
            this.password = password;
            this.request = request;
        }

        @Override
        public String userId() {
            return userId;
        }

        @Override
        public String password() {
            return password;
        }

        @Override
        public AbuRequest request() {
            return request;
        }
    }

    @Override
    public AbuResponse filter( AbuExecutionContext context, AbuRequest request, AbuHandler handler ) {

        Optional<String> paramOpt = request.header().mayGet( "Authorization" );
        if ( !paramOpt.isPresent() ) {
            return unauthorized( request );
        }

        String rawParam = paramOpt.get();
        if ( !rawParam.startsWith( "Basic " ) ) {
            return unauthorized( request );
        }

        String encoded = rawParam.substring( 6 );  // 6 = len("Basic ")
        byte[] decoded = Base64.getDecoder().decode( encoded );
        String paramStr = new String( decoded, StandardCharsets.UTF_8 );
        List<String> userAndPassword = splitter.splitToList( paramStr );

        if ( accepts.test( new AuthRequestImpl( userAndPassword.get( 0 ), userAndPassword.get( 1 ), request ) ) ) {
            return handler.handle( context, request );
        } else {
            return unauthorized( request );
        }
    }

    private AbuResponse unauthorized( AbuRequest request ) {
        return unauthorized
                .header( h -> h.add(
                        HttpHeaders.WWW_AUTHENTICATE,
                        "Basic realm=\"" + calcRealm.apply( request ) + "\""
                ) );
    }
}
