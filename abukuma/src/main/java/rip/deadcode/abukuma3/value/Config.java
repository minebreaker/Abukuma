package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.value.internal.ConfigImpl;

import java.util.Optional;


public interface Config {

    public Optional<String> serverImplementation();

    public ConfigImpl serverImplementation( String serverImplementation );

    public int port();

    public ConfigImpl port( int port );

    public int maxThreads();

    public ConfigImpl maxThreads( int maxThreads );

    public int minThreads();

    public ConfigImpl minThreads( int minThreads );

    public boolean ssl();

    public ConfigImpl ssl( boolean ssl );
}
