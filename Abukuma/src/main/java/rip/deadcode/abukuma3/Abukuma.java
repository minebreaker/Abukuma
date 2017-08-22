package rip.deadcode.abukuma3;

import com.twitter.util.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.config.Config;
import rip.deadcode.abukuma3.route.Router;

/**
 * Root object of Abukuma framework. Use this class to configure server, combine routes, and do other stuffs.
 */
public final class Abukuma {

    private static final Logger logger = LoggerFactory.getLogger(Abukuma.class);

    public static Builder config(Config config) {
        return new Builder().config(config);
    }

    public static final class Builder {

        private Config config;
        private Router router;

        public Builder config(Config config) {
            this.config = config;
            return this;
        }

        public Builder route(Router router) {
            this.router = router;
            return this;
        }

        public void run() {
            try {
                new AbukumaServer(config, router).run();
            } catch (TimeoutException | InterruptedException e) {
                logger.error("Fatal error!", e);
                throw new RuntimeException(e);
            }
        }

    }

}
