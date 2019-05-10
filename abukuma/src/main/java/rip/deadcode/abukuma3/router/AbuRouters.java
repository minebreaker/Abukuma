package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.router.internal.RouterSpecImpl;


public final class AbuRouters {

    private AbuRouters() {
        throw new Error();
    }

    public static RouterSpec create() {
        return new RouterSpecImpl();
    }
}
