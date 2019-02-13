package rip.deadcode.abukuma3.value;

public final class AbuConfig {

    private int port;
    private int maxThreads;
    private int minThreads;

    private AbuConfig() {
        this.port = 8080;
        this.maxThreads = 128;
        this.minThreads = 8;
    }

    private AbuConfig( int port ) {
        this.port = port;
    }

    public static AbuConfig create() {
        return new AbuConfig();
    }

    public AbuConfig copy() {
        return new AbuConfig( port );
    }

    public int port() {
        return port;
    }

    public AbuConfig port( int port ) {
        AbuConfig c = copy();
        c.port = port;
        return c;
    }

    public int maxThreads() {
        return maxThreads;
    }

    public AbuConfig maxThreads( int maxThreads ) {
        AbuConfig c = copy();
        c.maxThreads = maxThreads;
        return c;
    }

    public int minThreads() {
        return minThreads;
    }

    public AbuConfig minThreads( int minThreads ) {
        AbuConfig c = copy();
        c.minThreads = minThreads;
        return c;
    }
}
