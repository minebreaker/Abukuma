package rip.deadcode.abukuma3.utils.url.internal;


import rip.deadcode.abukuma3.utils.url.UrlQuery;
import rip.deadcode.abukuma3.utils.url.UrlQueryParser;


/**
 * This parser works like the PHP `parse_str` function.
 * Since PHP doesn't provide the detailed specification of the function,
 * 100% compatibility is not the aim of this class.
 *
 * @see <a href="https://www.php.net/manual/en/function.parse-str.php">PHP parse_str function</a>
 */
public final class PhpQueryParameterParser implements UrlQueryParser {
    @Override public UrlQuery parse( String query ) {
        throw new UnsupportedOperationException();  // TODO
    }
}
