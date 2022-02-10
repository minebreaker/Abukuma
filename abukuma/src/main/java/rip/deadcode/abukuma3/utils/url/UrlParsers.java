package rip.deadcode.abukuma3.utils.url;

import rip.deadcode.abukuma3.utils.url.internal.WhatwgUrlParser;


public final class UrlParsers {

    public static UrlParser standard() {
        return WhatwgUrlParser::parseStatic;
    }
}
