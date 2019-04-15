package rip.deadcode.abukuma3.value;


import rip.deadcode.abukuma3.value.internal.AbuConfigImpl;

import javax.annotation.concurrent.NotThreadSafe;


@NotThreadSafe
public final class PojoConfig {

    private int port;
    private int maxThreads;
    private int minThreads;

    public PojoConfig( int port, int maxThreads, int minThreads ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
    }

    public AbuConfig toConfig() {
        return new AbuConfigImpl( port, maxThreads, minThreads );
    }

    public int getPort() {
        return port;
    }

    public void setPort( int port ) {
        this.port = port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads( int maxThreads ) {
        this.maxThreads = maxThreads;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public void setMinThreads( int minThreads ) {
        this.minThreads = minThreads;
    }
}
