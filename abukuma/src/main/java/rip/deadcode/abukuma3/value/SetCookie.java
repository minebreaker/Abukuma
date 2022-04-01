package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.utils.url.internal.CookieImpl;


public final class SetCookie {

    private SetCookie() {
        throw new Error();
    }

    public static Cookie create( String name, String value ) {
        return new CookieImpl( name, value );
    }

    public static Cookie delete( String name ) {
        // empty domain should mean the current domain
        return new CookieImpl( name, "", 0, null, "/", false, false, null );
    }
}
