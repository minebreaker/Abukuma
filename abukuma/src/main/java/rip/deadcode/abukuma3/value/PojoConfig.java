package rip.deadcode.abukuma3.value;


import rip.deadcode.abukuma3.value.internal.ConfigImpl;

import javax.annotation.concurrent.NotThreadSafe;


@NotThreadSafe
public final class PojoConfig {

    private String serverImplementation;
    private int port;
    private int maxThreads;
    private int minThreads;

    public PojoConfig() {}

    public PojoConfig( int port, int maxThreads, int minThreads, String serverImplementation ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
        this.serverImplementation = serverImplementation;
    }

    public Config toConfig() {
        return new ConfigImpl( port, maxThreads, minThreads, serverImplementation );
    }

    public String getServerImplementation() {
        return serverImplementation;
    }

    public void setServerImplementation( String serverImplementation ) {
        this.serverImplementation = serverImplementation;
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
