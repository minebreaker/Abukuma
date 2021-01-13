package rip.deadcode.abukuma3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.internal.Information;
import rip.deadcode.abukuma3.internal.ServerSpecImpl;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;


public final class Abukuma {

    private static final Logger logger = LoggerFactory.getLogger( Abukuma.class );

    private Abukuma() {
        throw new Error();
    }

    public static ServerSpec create() {
        logger.info( Information.INFO_STRING );
        return new ServerSpecImpl(
                createList(),
                createList(),
                createList(),
                createList()
        );
    }
}
