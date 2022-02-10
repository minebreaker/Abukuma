package rip.deadcode.abukuma3.utils.url.internal;

import com.google.common.base.Strings;


public final class Utils {

    public static String toReadableCodepoint( char character ) {
        return "\\u" + Strings.padStart( Integer.toHexString( (int) character ), 4, '0' );
    }
}
