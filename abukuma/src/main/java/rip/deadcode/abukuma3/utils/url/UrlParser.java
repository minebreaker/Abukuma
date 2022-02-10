package rip.deadcode.abukuma3.utils.url;


import javax.annotation.Nullable;


/**
 * Converts given url string to the {@link UrlModel}.
 *
 * The parser implementation must be safely used for untrusted inputs.
 * The implementation must be resistant against attacks like ReDoS for example.
 */
public interface UrlParser {

     public default UrlParseResult parse( String url ) {
        return parse( url, null );
    }

     public UrlParseResult parse( String url, @Nullable UrlModel baseUrl );
}
