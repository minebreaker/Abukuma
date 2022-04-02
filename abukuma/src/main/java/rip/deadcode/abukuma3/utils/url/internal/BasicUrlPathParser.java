package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.UrlPathParseResult;
import rip.deadcode.abukuma3.utils.url.UrlPathParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapList;


public final class BasicUrlPathParser implements UrlPathParser {

    private static final Splitter pathSplitter = Splitter.on( "/" );

    @Override public UrlPathParseResult parse( String urlPath ) {
        return parseStatic( urlPath );
    }

    public static UrlPathParseResult parseStatic( String urlPath ) {
        return new UrlPathParseResultImpl.SuccessImpl(
                parseStaticUncheck( urlPath ),
                createList()
        );
    }

    public static PersistentList<String> parseStaticUncheck( String urlPath ) {
        String pathStr = urlPath.startsWith( "/" ) ? urlPath.substring( 1 ) : urlPath;
        List<String> paths = pathSplitter.splitToStream( pathStr )
                                         .map( s -> {
                                             try {
                                                 return URLDecoder.decode( s, "UTF-8" );
                                             } catch ( UnsupportedEncodingException e ) {
                                                 throw new RuntimeException( e );
                                             }
                                         } )
                                         .collect( toList() );
        return wrapList( paths );
    }
}
