package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.ImmutableList;
import rip.deadcode.abukuma3.value.AbuHeader;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.Cookie;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public final class AbuResponseImpl implements AbuResponse {

    private Object body;
    private int status;
    private AbuHeader header;
    private List<Cookie> cookie;

    public AbuResponseImpl( Object body, int status, AbuHeader header, List<Cookie> cookie ) {
        this.body = body;
        this.status = status;
        this.header = header;
        this.cookie = cookie;
    }

    private AbuResponseImpl copy() {
        return new AbuResponseImpl(
                body,
                status,
                header,
                cookie
        );
    }

    @Override public Object body() {
        return body;
    }

    @Override public AbuResponse body( Object body ) {
        AbuResponseImpl r = this.copy();
        r.body = body;
        return r;
    }

    @Override public int status() {
        return status;
    }

    @Override public AbuResponse status( int status ) {
        AbuResponseImpl r = this.copy();
        r.status = status;
        return r;
    }

    @Override public AbuHeader header() {
        return header;
    }

    @Override public AbuResponse header( AbuHeader header ) {
        AbuResponseImpl r = this.copy();
        r.header = header;
        return r;
    }

    @Override public AbuResponse header( Function<AbuHeader, AbuHeader> header ) {
        AbuResponseImpl r = this.copy();
        r.header = header.apply( this.header );
        return r;
    }

    @Override public List<Cookie> cookie() {
        return cookie;
    }

    @Override public Optional<Cookie> cookie( String key ) {
        return cookie.stream()
                     .filter( c -> c.name().equals( key ) )
                     .findAny();
    }

    @Override public AbuResponse addCookie( Cookie cookie ) {
        AbuResponseImpl r = copy();
        r.cookie = ImmutableList.<Cookie>builder()
                .addAll( this.cookie )
                .add( cookie )
                .build();
        return r;
    }

    @Override public AbuResponse addCookie( Cookie... cookie ) {
        AbuResponseImpl r = copy();
        r.cookie = ImmutableList.<Cookie>builder()
                .addAll( this.cookie )
                .add( cookie )
                .build();
        return r;
    }
}
