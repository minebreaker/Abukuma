package rip.deadcode.abukuma3.utils.url;


import javax.annotation.Nullable;


public class UrlParseException extends RuntimeException {

    public UrlParseException( @Nullable String message ) {
        super( message );
    }

    public UrlParseException( @Nullable String message, @Nullable Exception cause ) {
        super( message, cause );
    }

    public UrlParseException( @Nullable Exception cause ) {
        super( cause );
    }

    public static final class InvalidCharacter extends UrlParseException {

        public InvalidCharacter( String message ) {
            super( message, null );
        }
    }
}
