package rip.deadcode.abukuma3.utils.internal;

import rip.deadcode.abukuma3.utils.MimeDetector;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class DefaultMimeDetector implements MimeDetector {

    @Override public String detectMime( String pathAsString, @Nullable Path path ) {
        if ( path != null ) {
            try {
                return Files.probeContentType( path );
            } catch ( IOException e ) {
                return "application/octet-stream";
            }
        }
        return null;
    }
}
