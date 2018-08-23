package rip.deadcode.abukuma3.config;

public final class AbuConfig {

    private int port;

    private AbuConfig( int port ) {
        this.port = port;
    }

    public AbuConfig copy() {
        return new AbuConfig( port );
    }

    public static final class Builder {

        private AbuConfig config = new AbuConfig( 8080 );

        public Builder port( int port ) {
            config.port = port;
            return this;
        }

        public AbuConfig build() {
            return config;
        }
    }

    public AbuConfig port( int port ) {
        AbuConfig c = copy();
        c.port = port;
        return c;
    }

    public int getPort() {
        return port;
    }
}
