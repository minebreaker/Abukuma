package rip.deadcode.abukuma3;

import rip.deadcode.abukuma3.config.AbuConfig;
import rip.deadcode.abukuma3.internal.AbuServerImpl;
import rip.deadcode.abukuma3.router.AbuRouter;

public final class Abukuma {

    public static final class AbukumaConfigSpec {

        private final AbuConfig config;

        private AbukumaConfigSpec( AbuConfig config ) {
            this.config = config;
        }

        public AbukumaRouterSpec router( AbuRouter router ) {
            return new AbukumaRouterSpec( config, router );
        }
    }

    public static final class AbukumaRouterSpec {

        private final AbuConfig config;
        private final AbuRouter router;

        private AbukumaRouterSpec( AbuConfig config, AbuRouter router ) {
            this.config = config;
            this.router = router;
        }

        public AbuServer build() {
            return new AbuServerImpl( config, router );
        }
    }

    public static AbukumaConfigSpec config( AbuConfig config ) {
        return new AbukumaConfigSpec( config );
    }

}
