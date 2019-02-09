package rip.deadcode.abukuma3.gson.internal;

import rip.deadcode.abukuma3.gson.JsonBody;


@JsonBody
class SamplePojo {
    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo( String foo ) {
        this.foo = foo;
    }
}
