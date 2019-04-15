package rip.deadcode.abukuma3.gson;

import com.google.gson.Gson;
import rip.deadcode.abukuma3.gson.internal.GsonParser;
import rip.deadcode.abukuma3.gson.internal.GsonRenderer;
import rip.deadcode.abukuma3.parser.AbuParser;
import rip.deadcode.abukuma3.renderer.AbuRenderer;
import rip.deadcode.abukuma3.value.AbuConfig;
import rip.deadcode.abukuma3.value.PojoConfig;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public final class AbuGson {

    public static AbuParser<Object> parser() {
        return new GsonParser();
    }

    public static AbuRenderer renderer() {
        return new GsonRenderer();
    }

    public static AbuConfig config( Path path ) {
        try ( Reader r = Files.newBufferedReader( path, StandardCharsets.UTF_8 ) ) {
            return new Gson().fromJson( r, PojoConfig.class ).toConfig();

        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }
}
