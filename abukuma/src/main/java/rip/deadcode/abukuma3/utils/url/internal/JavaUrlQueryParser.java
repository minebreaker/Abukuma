package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.utils.url.UrlQuery;
import rip.deadcode.abukuma3.utils.url.UrlQueryParser;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;


public final class JavaUrlQueryParser implements UrlQueryParser {

    private static final Splitter splitter = Splitter.on( "&" );
    private static final Splitter kvSplitter = Splitter.on( "=" ).limit( 1 );

    @Override public UrlQuery parse( String query ) {
        return parseStatic( query );
    }

    public static UrlQuery parseStatic( String query ) {
        return splitter.splitToStream( query )
                       .reduce(
                               new UrlQueryImpl(),
                               ( acc, e ) -> {
                                   Iterator<String> kv = kvSplitter.split( e ).iterator();
                                   String key = kv.next();
                                   String value = kv.hasNext() ? kv.next() : "";
                                   return acc.add(
                                           URLDecoder.decode( key, StandardCharsets.UTF_8 ),
                                           URLDecoder.decode( value, StandardCharsets.UTF_8 )
                                   );
                               },
                               ( e1, e2 ) -> e1.concat( e2 )
                       );
    }
}
