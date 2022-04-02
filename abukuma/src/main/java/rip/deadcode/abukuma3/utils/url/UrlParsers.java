package rip.deadcode.abukuma3.utils.url;

import rip.deadcode.abukuma3.utils.url.internal.JavaUrlParser;


public final class UrlParsers {

    public static UrlParser standard() {
        return JavaUrlParser::parseStatic;
    }
}
