package rip.deadcode.abukuma3.value.internal;

import rip.deadcode.abukuma3.value.Config;


public final class ConfigImpl implements Config {

    private int port;
    private int maxThreads;
    private int minThreads;
    private boolean ssl;
    private com.typesafe.config.Config original;

    public ConfigImpl(
            int port,
            int maxThreads,
            int minThreads,
            boolean ssl,
            com.typesafe.config.Config original ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
        this.ssl = ssl;
        this.original = original;
    }

    private ConfigImpl copy() {
        return new ConfigImpl( port, maxThreads, minThreads, ssl, original );
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public ConfigImpl port( int port ) {
        ConfigImpl c = copy();
        c.port = port;
        return c;
    }

    @Override
    public int maxThreads() {
        return maxThreads;
    }

    @Override
    public ConfigImpl maxThreads( int maxThreads ) {
        ConfigImpl c = copy();
        c.maxThreads = maxThreads;
        return c;
    }

    @Override
    public int minThreads() {
        return minThreads;
    }

    @Override
    public ConfigImpl minThreads( int minThreads ) {
        ConfigImpl c = copy();
        c.minThreads = minThreads;
        return c;
    }

    @Override public boolean ssl() {
        return ssl;
    }

    @Override public ConfigImpl ssl( boolean ssl ) {
        ConfigImpl c = copy();
        c.ssl = ssl;
        return c;
    }

    @Override public com.typesafe.config.Config original() {
        return original;
    }
}
