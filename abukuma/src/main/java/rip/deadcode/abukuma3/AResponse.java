package rip.deadcode.abukuma3;

public final class AResponse {

    private Object body;

    public AResponse( Object body ) {
        this.body = body;
    }

    public Object getBody() {
        return this.body;
    }

    public AResponse body( Object body ) {
        AResponse r = this.copy();
        r.body = body;
        return r;
    }

    public AResponse copy() {
        return new AResponse(
                body
        );
    }
}
