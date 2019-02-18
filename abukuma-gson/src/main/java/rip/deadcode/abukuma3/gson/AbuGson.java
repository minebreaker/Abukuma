package rip.deadcode.abukuma3.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;


public final class AbuGson {

    private static final class Holder {
        private static final Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();
        private static final GsonParser parser = new GsonParser( gson );
        private static final GsonRenderer renderer = new GsonRenderer( gson );
    }

    // TODO Execution context to retrieve global gson instance?

    public static AbuParser<Object> parser() {
        return Holder.parser;
    }

    public static AbuRenderer renderer() {
        return Holder.renderer;
    }

    public static AbuParser<Object> parser( Gson gson ) {
        return new GsonParser( gson );
    }

    public static AbuRenderer renderer( Gson gson ) {
        return new GsonRenderer( gson );
    }
}
