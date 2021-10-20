package rip.deadcode.abukuma3.test;

import rip.deadcode.abukuma3.value.Response;

import java.nio.charset.Charset;


public interface TestResult {

    public Response response();

    public byte[] body();

    public String bodyAsString( Charset charset );
}
