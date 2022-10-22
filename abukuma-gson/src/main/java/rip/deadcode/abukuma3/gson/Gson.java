package rip.deadcode.abukuma3.gson;

import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;


public final class Gson {

    // FIXME

    public static Parser parser() {
        return new GsonParser( true, false );
    }

    public static Renderer renderer() {
        return new GsonRenderer( false );
    }
}
