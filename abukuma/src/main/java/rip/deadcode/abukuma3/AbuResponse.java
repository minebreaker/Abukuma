package rip.deadcode.abukuma3;

public final class AbuResponse {

    private Object body;

    public AbuResponse( Object body ) {
        this.body = body;
    }

    public Object getBody() {
        return this.body;
    }

    public AbuResponse body( Object body ) {
        AbuResponse r = this.copy();
        r.body = body;
        return r;
    }

    public AbuResponse copy() {
        return new AbuResponse(
                body
        );
    }
}
