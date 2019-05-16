package rip.deadcode.abukuma3.test;

import rip.deadcode.abukuma3.value.AbuResponse;

import java.nio.charset.Charset;


public interface TestResult {

    public AbuResponse response();

    public byte[] body();

    public String bodyAsString( Charset charset );
}
