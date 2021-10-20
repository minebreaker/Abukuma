package rip.deadcode.abukuma3.router.internal;


import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.collection.Tuple2;
import rip.deadcode.abukuma3.internal.utils.Uncheck;
import rip.deadcode.abukuma3.router.QueryParameterParser;

import java.net.URLDecoder;
import java.util.List;


/**
 * A default implementation of {@link QueryParameterParser}.
 * The implementation is based on the
 * <a href="https://url.spec.whatwg.org/#urlencoded-parsing">WHATWG §5.1 application/x-www-form-urlencoded parsing</a>.
 */
public final class DefaultQueryParameterParser implements QueryParameterParser {

    private static final Splitter ampersandSeparator = Splitter.on( "&" );
    private static final Splitter equalSeparator = Splitter.on( "=" ).limit( 2 );

    @Override public List<Tuple2<String, String>> parse( String urlQuery ) {

        // Later rewrite with `splitToStream()`
//        return ampersandSeparator.splitToList( urlQuery ).stream()
//                                 .map( pair -> equalSeparator.splitToList( pair ) )
//                                 .reduce(
//                                         PersistentCollections.createMultimap(),
//                                         ( m, pair ) -> m.add(
//                                                 percentDecode( pair.get( 0 ) ),
//                                                 percentDecode( pair.get( 1 ) )
//                                         ),
//                                         PersistentMultimap::merge
//                                 );
        throw new UnsupportedOperationException();
    }

    private static String percentDecode( String str ) {
        // TODO Rethink that URLDecode.decode() is a right choice
        return Uncheck.uncheck( () -> URLDecoder.decode( str, "UTF-8" ) );
    }

//    private static PersistentMultimap<String, String> parseChar( String query, int i ) {
//
//    }
//
//    // https://url.spec.whatwg.org/#url-code-points
//    private static boolean isUrlCodePoint( int cp ) {
//        return ( ( cp >= 'a' && cp <= 'z' ) || ( cp >= 'A' && cp <= 'Z' ) || ( cp >= '0' && cp <= '9' ) )
//               // ASCII Alphanumeric
//               || cp == '!' || cp == '$' || cp == '&' || cp == '\'' || cp == '(' || cp == ')' || cp == '*'
//               || cp == '+' || cp == ',' || cp == '.' || cp == '/' || cp == ':' || cp == ';' || cp == '='
//               || cp == '?' || cp == '@' || cp == '_' || cp == '~'
//               // CP range check
//               || ( cp >= 0xA0 && cp <= 0x10FFFD &&
//                    // Noncharacter check
//                    !( ( cp >= 0xFDD0 && cp <= 0xFDEF ) || cp == 0xFFFE || cp == 0xFFFF
//                       || cp == 0x1FFFE || cp == 0x1FFFF || cp == 0x2FFFE || cp == 0x2FFFF
//                       || cp == 0x3FFFE || cp == 0x3FFFF || cp == 0x4FFFE || cp == 0x4FFFF
//                       || cp == 0x5FFFE || cp == 0x5FFFF || cp == 0x6FFFE || cp == 0x6FFFF
//                       || cp == 0x7FFFE || cp == 0x7FFFF || cp == 0x8FFFE || cp == 0x8FFFF
//                       || cp == 0x9FFFE || cp == 0x9FFFF || cp == 0xAFFFE || cp == 0xAFFFF
//                       || cp == 0xBFFFE || cp == 0xBFFFF || cp == 0xCFFFE || cp == 0xCFFFF
//                       || cp == 0xDFFFE || cp == 0xDFFFF || cp == 0xEFFFE || cp == 0xEFFFF )
//                    // Surrogate check
//                    && !( cp >= 0xD800 && cp <= 0xDFFF )
//               );
//    }
}
