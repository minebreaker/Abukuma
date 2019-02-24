package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.value.internal.AbuConfigImpl;


public final class AbuConfigs {

    public static AbuConfig create() {
        return new AbuConfigImpl();
    }
}
