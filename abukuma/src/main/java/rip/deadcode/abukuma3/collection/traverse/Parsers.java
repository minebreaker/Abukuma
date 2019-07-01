package rip.deadcode.abukuma3.collection.traverse;

import java.util.OptionalInt;


public final class Parsers {

    public static OptionalInt parseInt( String str ) {
        try {
            return OptionalInt.of( Integer.parseInt( str ) );
        } catch ( NumberFormatException e ) {
            return OptionalInt.empty();
        }
    }
}
