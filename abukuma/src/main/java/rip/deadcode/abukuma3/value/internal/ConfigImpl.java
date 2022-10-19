package rip.deadcode.abukuma3.value.internal;

import rip.deadcode.abukuma3.value.Config;

import javax.annotation.Nullable;
import java.util.Optional;


public final class ConfigImpl implements Config {

    @Nullable
    private String serverImplementation;
    private int port;
    private int maxThreads;
    private int minThreads;
    private boolean ssl;

    public ConfigImpl( @Nullable String serverImplementation, int port, int maxThreads, int minThreads, boolean ssl ) {
        this.serverImplementation = serverImplementation;
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
        this.ssl = ssl;
    }

    private ConfigImpl copy() {
        return new ConfigImpl( serverImplementation, port, maxThreads, minThreads, ssl );
    }

    @Override
    public Optional<String> serverImplementation() {
        return Optional.ofNullable( serverImplementation );
    }

    @Override
    public ConfigImpl serverImplementation( String serverImplementation ) {
        ConfigImpl c = copy();
        c.serverImplementation = serverImplementation;
        return c;
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
}
