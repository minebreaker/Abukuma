package rip.deadcode.abukuma3;

public final class Abukuma {

    public static final class AbukumaConfigSpec {

        private final AConfig config;

        private AbukumaConfigSpec( AConfig config ) {
            this.config = config;
        }

        public AbukumaRouterSpec router( ARouter router ) {
            return new AbukumaRouterSpec( config, router );
        }
    }

    public static final class AbukumaRouterSpec {

        private final AConfig config;
        private final ARouter router;

        private AbukumaRouterSpec( AConfig config, ARouter router ) {
            this.config = config;
            this.router = router;
        }

        public AbukumaServer build() {
            return new AbukumaServerImpl( config, router );
        }
    }

    public static AbukumaConfigSpec config( AConfig config ) {
        return new AbukumaConfigSpec( config );
    }

}
