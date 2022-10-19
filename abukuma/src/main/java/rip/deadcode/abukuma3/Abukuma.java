package rip.deadcode.abukuma3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.internal.ConfigSpecImpl;
import rip.deadcode.abukuma3.internal.Information;


public final class Abukuma {

    private static final Logger logger = LoggerFactory.getLogger( Abukuma.class );

    private Abukuma() {
        throw new Error();
    }

    public static ServerSpec create() {
        logger.info( Information.INFO_STRING );
        return new ConfigSpecImpl().useDefault();
    }

    public static ConfigSpec config() {
        logger.info( Information.INFO_STRING );
        return new ConfigSpecImpl();
    }
}
