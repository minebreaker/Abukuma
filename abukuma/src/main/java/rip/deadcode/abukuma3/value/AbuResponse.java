package rip.deadcode.abukuma3.value;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public final class AbuResponse {

    private Object body;
    private int status;
    private AbuHeader header;
    private List<Cookie> cookie;

    private AbuResponse( Object body, int status, AbuHeader header, List<Cookie> cookie ) {
        this.body = body;
        this.status = status;
        this.header = header;
        this.cookie = cookie;
    }

    public static AbuResponse create( Object body ) {
        return new AbuResponse( body, 200, AbuHeader.create(), ImmutableList.of() );
    }

    public AbuResponse copy() {
        return new AbuResponse(
                body,
                status,
                header,
                cookie
        );
    }

    public Object body() {
        return body;
    }

    public AbuResponse body( Object body ) {
        AbuResponse r = this.copy();
        r.body = body;
        return r;
    }

    public int status() {
        return status;
    }

    public AbuResponse status( int status ) {
        AbuResponse r = this.copy();
        r.status = status;
        return r;
    }

    public AbuHeader header() {
        return header;
    }

    public AbuResponse header( AbuHeader header ) {
        AbuResponse r = this.copy();
        r.header = header;
        return r;
    }

    public AbuResponse header( Function<AbuHeader, AbuHeader> header ) {
        AbuResponse r = this.copy();
        r.header = header.apply( this.header );
        return r;
    }

    public List<Cookie> cookie() {
        return cookie;
    }

    public Optional<Cookie> cookie( String key ) {
        return cookie.stream()
                     .filter( c -> c.name().equals( key ) )
                     .findAny();
    }

    public AbuResponse addCookie( Cookie cookie ) {
        AbuResponse r = copy();
        r.cookie = ImmutableList.<Cookie>builder()
                .addAll( this.cookie )
                .add( cookie )
                .build();
        return r;
    }

    public AbuResponse addCookie( Cookie... cookie ) {
        AbuResponse r = copy();
        r.cookie = ImmutableList.<Cookie>builder()
                .addAll( this.cookie )
                .add( cookie )
                .build();
        return r;
    }
}
