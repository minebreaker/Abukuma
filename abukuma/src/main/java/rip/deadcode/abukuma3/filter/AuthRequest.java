package rip.deadcode.abukuma3.filter;

import rip.deadcode.abukuma3.value.AbuRequest;


public interface AuthRequest {

    public String userId();

    public String password();

    public AbuRequest request();
}
