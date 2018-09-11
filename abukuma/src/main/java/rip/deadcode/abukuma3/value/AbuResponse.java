package rip.deadcode.abukuma3.value;

import java.util.function.Function;

public final class AbuResponse {

    private Object body;
    private AbuHeader header;

    private AbuResponse( Object body, AbuHeader header ) {
        this.body = body;
        this.header = header;
    }

    public static AbuResponse create( Object body ) {
        return new AbuResponse( body, AbuHeader.create() );
    }

    public AbuResponse copy() {
        return new AbuResponse(
                body,
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
