package rip.deadcode.abukuma3.utils.url;

import rip.deadcode.abukuma3.utils.url.internal.JavaUrlQueryParser;


public final class UrlQueryParsers {

    public static UrlQueryParser standard() {
        return JavaUrlQueryParser::parseStatic;
    }
}
