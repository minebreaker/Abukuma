package rip.deadcode.abukuma3.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.SocketAddress;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class MutableConfig implements Config {

    private SocketAddress address;
    private String contextPath;

}
