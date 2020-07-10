package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.value.Request;


public interface AuthRequest {

    public String userId();

    public String password();

    public Request request();
}
