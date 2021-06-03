package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.router.PathParser;
import rip.deadcode.abukuma3.router.PathParsingResult;

import javax.annotation.Nullable;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.collection.PersistentCollections.wrapList;
import static rip.deadcode.abukuma3.internal.utils.MoreMoreObjects.coalesce;


public final class DefaultPathParser implements PathParser {

    @Override public PathParsingResult parse( String urlPath ) {
        return parseRequestTarget( urlPath );
    }

    private static PathParsingResult parseRequestTarget( String s ) {
        // null indicates a parsing failure
        return coalesce(
                parseOriginForm( s ),
                () -> parseAbsoluteForm( s ),
                () -> parseAuthorityForm( s ),
                () -> parseAsteriskForm( s )
        ).orElseThrow( () -> new RuntimeException( "Malformed path" ) );  // TODO: Rethink about error handling
    }

    @Nullable
    private static PathParsingResult parseOriginForm( String s ) {
        if ( s.startsWith( "/" ) ) {
            int secondSlashIndex = s.indexOf( '/', 1 );  // An index of the second slash
            if ( secondSlashIndex == -1 ) {
                int queryIndex = s.indexOf( "?" );
                if ( queryIndex == -1 ) {
                    return new PathParsingResultImpl( createList( s.substring( 1 ) ), "" );
                } else {
                    return new PathParsingResultImpl(
                            createList( s.substring( 1, queryIndex ) ),
                            s.substring( queryIndex )
                    );
                }
            } else {
                String part = s.substring( 1, secondSlashIndex );
                String rest = s.substring( secondSlashIndex );
                if (rest.isEmpty()) {
                    return new PathParsingResultImpl(
                            createList(part),
                            ""
                    );
                } else {
                    PathParsingResult restParsed = parseOriginForm( rest );
                    if ( restParsed == null ) {
                        return null;
                    }
                    return new PathParsingResultImpl(
                            wrapList( restParsed.paths() ).addFirst( part ),
                            restParsed.rest()
                    );
                }
            }

        } else {
            return null;
        }
    }

    @Nullable
    private static PathParsingResult parseAbsoluteForm( String s ) {
        return null;  // TODO Proxy is not supported yet.
    }

    @Nullable
    private static PathParsingResult parseAuthorityForm( String s ) {
        return null;  // TODO Proxy is not supported yet.
    }

    @Nullable
    private static PathParsingResult parseAsteriskForm( String s ) {
        if ( s.startsWith( "*" ) ) {
            return new PathParsingResultImpl( createList( "*" ), s.substring( 1 ) );
        } else {
            return null;
        }
    }
}
