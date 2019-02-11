package rip.deadcode.abukuma3.value;

import java.util.function.Function;

public final class AbuResponse {

    private Object body;
    private int status;
    private AbuHeader header;

    private AbuResponse( Object body, int status, AbuHeader header ) {
        this.body = body;
        this.status = status;
        this.header = header;
    }

    public static AbuResponse create( Object body ) {
        return new AbuResponse( body, 200, AbuHeader.create() );
    }

    public AbuResponse copy() {
        return new AbuResponse(
                body,
                status,
                header
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
}
