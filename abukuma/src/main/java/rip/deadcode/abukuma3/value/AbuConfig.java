package rip.deadcode.abukuma3.value;

public final class AbuConfig {

    private int port;

    private AbuConfig() {
        this.port = 8080;
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
}
