package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.value.internal.AbuConfigImpl;


public interface AbuConfig {

    public int port();

    public AbuConfigImpl port( int port );

    public int maxThreads();

    public AbuConfigImpl maxThreads( int maxThreads );

    public int minThreads();

    public AbuConfigImpl minThreads( int minThreads );
}
