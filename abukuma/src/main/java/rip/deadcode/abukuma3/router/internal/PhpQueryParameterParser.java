package rip.deadcode.abukuma3.router.internal;

import rip.deadcode.abukuma3.collection.Tuple2;
import rip.deadcode.abukuma3.router.QueryParameterParser;

import java.util.List;


/**
 * This parser works like the PHP `parse_str` function.
 * Since PHP doesn't provide the detailed specification of the function,
 * 100% compatibility is not the aim of this class.
 *
 * @see <a href="https://www.php.net/manual/en/function.parse-str.php">PHP parse_str function</a>
 */
public final class PhpQueryParameterParser implements QueryParameterParser {

    private boolean allowDuplicateKeys;
    private boolean replaceValues;

    @Override public List<Tuple2<String, String>> parse( String urlQuery ) {
        throw new UnsupportedOperationException();
    }
}
