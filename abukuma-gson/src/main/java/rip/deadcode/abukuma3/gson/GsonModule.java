package rip.deadcode.abukuma3.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.typesafe.config.ConfigFactory;
import rip.deadcode.abukuma3.Module;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.gson.internal.GsonConfig;
import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.internal.RegistryUtils;


public final class GsonModule implements Module {

    @Override public Registry apply( Registry registry ) {

        var config = loadConfig();

        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .serializeNulls()
                .create();

        Registry r = registry.setSingleton(
                Gson.class,
                gson
        );

        r = RegistryUtils.addParser( r, new GsonParser( config.requireJsonMimeType(), config.requireAnnotation() ) );
        r = RegistryUtils.addRenderer( r, new GsonRenderer( config.requireAnnotation() ) );

        return r;
    }

    private static GsonConfig loadConfig() {

        var rootConfig = ConfigFactory.load();
        rootConfig.checkValid( ConfigFactory.defaultReference(), "abukuma.gson" );
        var config = rootConfig.getConfig( "abukuma.gson" );

        return new GsonConfig(
                config.getBoolean( "requireJsonMimeType" ),
                config.getBoolean( "requireAnnotation" )
        );
    }
}
