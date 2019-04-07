package rip.deadcode.abukuma3.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.internal.RegistryUtils;


public final class GsonModule implements Module {

    private static final GsonModule instance = new GsonModule();

    public static GsonModule getInstance() {
        return instance;
    }

    @Override public Registry apply( Registry registry ) {

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();

        Registry r = registry.setSingleton(
                Gson.class,
                gson
        );

        r = RegistryUtils.addParser( r, new GsonParser() );
        r = RegistryUtils.addRenderer( r, new GsonRenderer() );

        return r;
    }
}
