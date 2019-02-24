package rip.deadcode.abukuma3.value.internal;

import rip.deadcode.abukuma3.value.AbuConfig;


public final class AbuConfigImpl implements AbuConfig {

    private int port;
    private int maxThreads;
    private int minThreads;

    public AbuConfigImpl() {
        this.port = 8080;
        this.maxThreads = 128;
        this.minThreads = 8;
    }

    private AbuConfigImpl( int port, int maxThreads, int minThreads ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
    }

    public AbuConfigImpl copy() {
        return new AbuConfigImpl( port, maxThreads, minThreads );
    }

    public int port() {
        return port;
    }

    public AbuConfigImpl port( int port ) {
        AbuConfigImpl c = copy();
        c.port = port;
        return c;
    }

    public int maxThreads() {
        return maxThreads;
    }

    public AbuConfigImpl maxThreads( int maxThreads ) {
        AbuConfigImpl c = copy();
        c.maxThreads = maxThreads;
        return c;
    }

    public int minThreads() {
        return minThreads;
    }

    public AbuConfigImpl minThreads( int minThreads ) {
        AbuConfigImpl c = copy();
        c.minThreads = minThreads;
        return c;
    }
}
