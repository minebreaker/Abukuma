package rip.deadcode.abukuma3.filter.internal;

import com.google.common.base.Splitter;
import com.google.common.net.HttpHeaders;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.filter.AuthRequest;
import rip.deadcode.abukuma3.filter.Filter;
import rip.deadcode.abukuma3.filter.Filters;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static rip.deadcode.abukuma3.filter.Filters.createSuccessfulBeforeFilterResult;


public class BasicAuthFilter implements Filter {

    private static final Response unauthorized = Responses
            .create( "401 Unauthorized" )
            .status( 401 );  // TODO

    private static final Splitter splitter = Splitter.on( ":" ).limit( 2 );

    private final Predicate<AuthRequest> accepts;
    private final Function<Request<?>, String> calcRealm;

    public BasicAuthFilter( Predicate<AuthRequest> accepts ) {
        this( accepts, req -> "default" );
    }

    public BasicAuthFilter( Predicate<AuthRequest> accepts, Function<Request<?>, String> calcRealm ) {
        this.accepts = accepts;
        this.calcRealm = calcRealm;
    }

    private static final class AuthRequestImpl implements AuthRequest {

        private final String userId;
        private final String password;
        private final Request<?> request;

        private AuthRequestImpl( String userId, String password, Request<?> request ) {
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
        public Request<?> request() {
            return request;
        }
    }

    @Override public BeforeFilterResult filterBefore( ExecutionContext context, Request<?> request ) {

        Optional<String> paramOpt = request.header().mayGet( "Authorization" );
        if ( paramOpt.isEmpty() ) {
            return unauthorized( request );
        }

        String rawParam = paramOpt.get();
        if ( !rawParam.startsWith( "Basic " ) ) {
            return unauthorized( request );
        }

        // FIXME: should accept multiple spaces? need check specs
        String encoded = rawParam.substring( 6 );  // 6 = len("Basic ")
        byte[] decoded = Base64.getDecoder().decode( encoded );
        String paramStr = new String( decoded, StandardCharsets.UTF_8 );
        List<String> userAndPassword = splitter.splitToList( paramStr );

        if ( accepts.test( new AuthRequestImpl( userAndPassword.get( 0 ), userAndPassword.get( 1 ), request ) ) ) {
            return createSuccessfulBeforeFilterResult( request );
        } else {
            return unauthorized( request );
        }
    }

    @Override public AfterFilterResult filterAfter( ExecutionContext context, Request<?> request, Response response ) {
        return null;
    }

    private Filter.InterruptedBeforeFilterResult unauthorized( Request<?> request ) {
        return Filters.createInterruptedBeforeFilterResult(
                unauthorized
                        .header( h -> h.add(
                                HttpHeaders.WWW_AUTHENTICATE,
                                "Basic realm=\"" + calcRealm.apply( request ) + "\""
                        ) )
        );
    }
}
