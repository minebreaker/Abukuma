package rip.deadcode.abukuma3.router.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.last;


public final class RoutingUtils {

    private RoutingUtils() {
        throw new Error();
    }

    public static final Splitter pathSplitter = Splitter.on( "/" ).omitEmptyStrings().trimResults();

    private static final Splitter extensionSplitter = Splitter.on( "." ).omitEmptyStrings();

    // TODO DI
    // don't use for unsafe source!
    @VisibleForTesting
    static String guessMediaType( String fileName ) {
        String extension = last( extensionSplitter.split( fileName ) ).toLowerCase();
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
