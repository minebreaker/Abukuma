package rip.deadcode.abukuma3.router.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.handler.AbuHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Supplier;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.last;


public final class UriHandler implements AbuHandler {

    private static final Splitter extensionSplitter = Splitter.on( "." ).omitEmptyStrings();

    private final Supplier<URI> uri;

    private UriHandler( Supplier<URI> uri ) {
        this.uri = uri;
    }

    public static UriHandler create( Supplier<URI> uri ) {
        return new UriHandler( uri );
    }

    @Override
    public AbuResponse handle( AbuRequest request ) {
        return handle( request, uri.get() );
    }

    static AbuResponse handle( AbuRequest request, URI uri ) {
        // TODO may add `Content-Disposition: attachment; filename=`?
        // TODO cache

        try {
            return AbuResponses.create( Files.newInputStream( Paths.get( uri ) ) )  // FIXME FileSystem
                               .header( h -> h.contentType( guessMediaType( uri.toString() ) ) );
        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }

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
