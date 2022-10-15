package rip.deadcode.abukuma3.gson;

import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.parser.Parser;
import rip.deadcode.abukuma3.renderer.Renderer;
import rip.deadcode.abukuma3.value.Config;
import rip.deadcode.abukuma3.value.PojoConfig;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public final class Gson {

    public static Parser parser() {
        return new GsonParser();
    }

    public static Renderer renderer() {
        return new GsonRenderer();
    }

    public static Config config( Path path ) {
        try ( Reader r = Files.newBufferedReader( path, StandardCharsets.UTF_8 ) ) {
            return new com.google.gson.Gson().fromJson( r, PojoConfig.class ).toConfig();

        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }
}
