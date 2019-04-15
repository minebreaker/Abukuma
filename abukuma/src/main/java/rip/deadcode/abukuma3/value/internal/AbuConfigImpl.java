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

    public AbuConfigImpl( int port, int maxThreads, int minThreads ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
    }

    private AbuConfigImpl copy() {
        return new AbuConfigImpl( port, maxThreads, minThreads );
    }

    @Override public int port() {
        return port;
    }

    @Override public AbuConfigImpl port( int port ) {
        AbuConfigImpl c = copy();
        c.port = port;
        return c;
    }

    @Override public int maxThreads() {
        return maxThreads;
    }

    @Override public AbuConfigImpl maxThreads( int maxThreads ) {
        AbuConfigImpl c = copy();
        c.maxThreads = maxThreads;
        return c;
    }

    @Override public int minThreads() {
        return minThreads;
    }

    @Override public AbuConfigImpl minThreads( int minThreads ) {
        AbuConfigImpl c = copy();
        c.minThreads = minThreads;
        return c;
    }
}
