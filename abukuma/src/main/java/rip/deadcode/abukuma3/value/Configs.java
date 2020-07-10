package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.value.internal.ConfigImpl;


public final class Configs {

    private Configs() {
        throw new Error();
    }

    public static Config create() {
        return new ConfigImpl();
    }
}
