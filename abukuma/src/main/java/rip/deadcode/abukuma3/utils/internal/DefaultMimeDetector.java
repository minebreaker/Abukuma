package rip.deadcode.abukuma3.utils.internal;

import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.utils.MimeDetector;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.last;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;


public class DefaultMimeDetector implements MimeDetector {

    @Override public String detect( String pathAsString, @Nullable Path path ) {

        return Optional.ofNullable( path )
                       // Should check if file exists?
                       .flatMap( p -> possibly( () -> Files.probeContentType( p ) ).asOptional() )
                       .orElseGet( () -> extensionBasedGuess( pathAsString ) );
    }

    private static final Splitter extensionSplitter = Splitter.on( "." ).omitEmptyStrings();

    private static String getExtension( String fileName ) {
        return last( extensionSplitter.split( fileName ) ).toLowerCase();
    }

    private static String extensionBasedGuess( String path ) {

        String extension = getExtension( path );

        switch ( extension ) {

        // TEXT
        case "html":
            return "text/html";
        case "css":
            return "text/css";
        case "txt":
            return "text/plain";

        // APPLICATION
        case "js":
            return "application/javascript";
        case "json":
            return "application/json";
        case "xml":
            return "application/xml";

        // IMAGE
        case "gif":
            return "image/gif";
        case "jpg":
        case "jpeg":
            return "image/jpeg";
        case "png":
            return "image/png";
        case "svg":
            return "image/svg+xml";
        case "tif":
        case "tiff":
            return "image/tiff";

        // AUDIO
        case "wav":
            return "audio/wav";
        case "mp3":
            return "audio/mpeg";
        case "mid":
        case "midi":
            return "audio/midi";
        case "aif":
        case "aiff":
            return "audio/aiff";

        // VIDEO
        case "mp4":
            return "video/mp4";
        case "mpg":
        case "mpeg":
            return "video/mpeg";
        case "mov":
        case "qt":
            return "video/quicktime";
        case "ogg":
            return "video/ogg";  // audio?

        default:
            // application/octet-stream may be better?
            return "*/*";
        }
    }
}
