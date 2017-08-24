package rip.deadcode.abukuma3.config;

import java.net.SocketAddress;

import static com.google.common.base.Preconditions.checkNotNull;

public interface Config {

    public SocketAddress getAddress();

    public String getContextPath();

    public default void validate() {
        checkNotNull(getAddress());
        checkNotNull(getContextPath());
    }

}
