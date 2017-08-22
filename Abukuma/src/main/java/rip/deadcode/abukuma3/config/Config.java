package rip.deadcode.abukuma3.config;

import java.net.SocketAddress;

public interface Config {

    public SocketAddress getAddress();
    public String getContextPath();

}
