package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.UrlModel;
import rip.deadcode.abukuma3.utils.url.UrlParseException;
import rip.deadcode.abukuma3.utils.url.UrlParseResult;
import rip.deadcode.abukuma3.utils.url.UrlParser;
import rip.deadcode.abukuma3.utils.url.UrlQueryParsers;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;


/**
 * URL parser that is based on the standard Java API {@link URI} implementation.
 */
public final class JavaUrlParser implements UrlParser {

    private static final Splitter userInfoSplitter = Splitter.on( ":" ).limit( 2 );

    @Override public UrlParseResult parse( String url, @Nullable UrlModel baseUrl ) {
        return parseStatic( url, baseUrl );
    }

    public static UrlParseResult parseStatic( String url, @Nullable UrlModel baseUrl ) {
        try {
            URI uri = new URI( url );

            String userInfo = uri.getUserInfo();
            String username = null;
            String password = null;
            if ( userInfo != null ) {
                Iterator<String> userInfos = userInfoSplitter.split( userInfo ).iterator();
                username = userInfos.next();
                if ( userInfos.hasNext() ) {
                    password = userInfos.next();
                }
            }
            // UrlPathParser should decode them.
            PersistentList<String> paths = BasicUrlPathParser.parseStaticUncheck( uri.getRawPath() );

            UrlModel model = new UrlModelImpl(
                    uri.getScheme(),
                    username,
                    password,
                    uri.getHost(),
                    uri.getPort(),
                    paths,
                    // UrlQueryParser should decode them.
                    UrlQueryParsers.standard().parse( uri.getRawQuery() ),
                    uri.getFragment()
            );
            return new UrlParseResultImpl.SuccessImpl( model, createList() );

        } catch ( URISyntaxException e ) {
            return new UrlParseResultImpl.ErrorImpl( createList( new UrlParseException( e ) ) );
        }
    }
}
