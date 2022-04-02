package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.utils.url.UrlQuery;
import rip.deadcode.abukuma3.utils.url.UrlQueryParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
                                   try {
                                       return acc.add(
                                               URLDecoder.decode( key, "UTF-8" ),
                                               URLDecoder.decode( value, "UTF-8" )
                                       );
                                   } catch ( UnsupportedEncodingException ex ) {
                                       throw new RuntimeException( ex );
                                   }
                               },
                               ( e1, e2 ) -> e1.concat( e2 )
                       );
    }
}
