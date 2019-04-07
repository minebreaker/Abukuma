package rip.deadcode.abukuma3.gson;

import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;


public final class AbuGson {

    public static AbuParser<Object> parser() {
        return new GsonParser();
    }

    public static AbuRenderer renderer() {
        return new GsonRenderer();
    }
}
