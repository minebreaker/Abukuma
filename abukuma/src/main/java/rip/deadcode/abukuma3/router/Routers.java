package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.router.internal.RouterSpecImpl;


public final class Routers {

    private Routers() {
        throw new Error();
    }

    public static RouterSpec create() {
        return new RouterSpecImpl();
    }
}
