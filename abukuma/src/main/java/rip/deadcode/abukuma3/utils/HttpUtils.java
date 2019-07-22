package rip.deadcode.abukuma3.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;


public final class HttpUtils {

    public static ZonedDateTime parseHttpDate( CharSequence httpDate ) {
        return ZonedDateTime.parse( httpDate, DateTimeFormatter.RFC_1123_DATE_TIME );
    }

    public static String formatHttpDate( LocalDateTime dateTime ) {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format( dateTime.atZone( ZoneOffset.UTC ) );
    }

    public static String formatHttpDate( Instant timestamp ) {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format( timestamp.atZone( ZoneOffset.UTC ) );
    }

    public static String formatHttpDate( TemporalAccessor dateTime ) {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format( dateTime );
    }
}
