package rip.deadcode.abukuma3.utils.url.internal;

import rip.deadcode.abukuma3.utils.url.UrlParseException;
import rip.deadcode.abukuma3.utils.url.UrlPathParseResult;
import rip.deadcode.abukuma3.utils.url.UrlPathParser;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


public final class DefaultPathParser implements UrlPathParser {

    @Override public UrlPathParseResult parse( String urlPath ) {
        return parseRequestTarget( urlPath );
    }

    private static UrlPathParseResult parseRequestTarget( String s ) {
        // null indicates a parsing failure
        return coalesce(
                parseOriginForm( s ),
                () -> parseAbsoluteForm( s ),
                () -> parseAuthorityForm( s ),
                () -> parseAsteriskForm( s )
        ).orElse( new UrlPathParseResultImpl.ErrorImpl( createList( new UrlParseException( "Failed to parse." ) ) ) );
    }

    private static UrlPathParseResult parseOriginForm( String s ) {
        if ( s.startsWith( "/" ) ) {
            int secondSlashIndex = s.indexOf( '/', 1 );  // An index of the second slash
            if ( secondSlashIndex == -1 ) {
                int queryIndex = s.indexOf( "?" );
                if ( queryIndex == -1 ) {
                    return new UrlPathParseResultImpl.SuccessImpl( createList( s.substring( 1 ) ), createList() );
                } else {
                    return new UrlPathParseResultImpl.SuccessImpl(
                            createList( s.substring( 1, queryIndex ) ),
                            createList()
                    );
                }
            } else {
                String part = s.substring( 1, secondSlashIndex );
                String rest = s.substring( secondSlashIndex );
                if ( rest.isEmpty() ) {
                    return new UrlPathParseResultImpl.SuccessImpl( createList( part ), createList() );
                } else {
                    UrlPathParseResult restParsed = parseOriginForm( rest );
                    if ( restParsed instanceof UrlPathParseResult.Success ) {
                        return new UrlPathParseResultImpl.SuccessImpl(
                                ( (UrlPathParseResult.Success) restParsed ).result().addFirst( part ),
                                createList()
                        );
                    } else {
                        return restParsed;
                    }
                }
            }

        } else {
            return null;
        }
    }

    private static UrlPathParseResult parseAbsoluteForm( String s ) {
        return null;  // TODO Proxy is not supported yet.
    }

    private static UrlPathParseResult parseAuthorityForm( String s ) {
        return null;  // TODO Proxy is not supported yet.
    }

    private static UrlPathParseResult parseAsteriskForm( String s ) {
        if ( s.startsWith( "*" ) ) {
            return new UrlPathParseResultImpl.SuccessImpl( createList( "*" ), createList() );
        } else {
            return null;
        }
    }
}
