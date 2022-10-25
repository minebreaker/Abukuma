package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.Unsafe;
import rip.deadcode.abukuma3.value.internal.ConfigImpl;


public interface Config {

    public int port();

    public ConfigImpl port( int port );

    public int maxThreads();

    public ConfigImpl maxThreads( int maxThreads );

    public int minThreads();

    public ConfigImpl minThreads( int minThreads );

    public boolean ssl();

    public ConfigImpl ssl( boolean ssl );

    @Unsafe
    public com.typesafe.config.Config original();
}
