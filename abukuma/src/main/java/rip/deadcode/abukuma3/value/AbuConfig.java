package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.value.internal.AbuConfigImpl;

import java.util.Optional;


public interface AbuConfig {

    public Optional<String> serverImplementation();

    public AbuConfigImpl serverImplementation( String serverImplementation );

    public int port();

    public AbuConfigImpl port( int port );

    public int maxThreads();

    public AbuConfigImpl maxThreads( int maxThreads );

    public int minThreads();

    public AbuConfigImpl minThreads( int minThreads );
}
