package rip.deadcode.abukuma3.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.google.common.truth.Truth.assertThat;


class HttpHeaderUtilsTest {

    @Test
    void testParseHttpDate() {

        String dateTime = "Sat, 01 Jan 2000 00:00:00 GMT";

        ZonedDateTime result = HttpUtils.parseHttpDate( dateTime );

        assertThat( result ).isEqualTo( ZonedDateTime.of( 2000, 1, 1, 0, 0, 0, 0, ZoneId.of( "Z" ) ) );
    }

    @Test
    void testFormatHttpDateForLocalDate() {

        LocalDateTime dateTime = LocalDateTime.of( 2000, 1, 1, 0, 0, 0 );

        String result = HttpUtils.formatHttpDate( dateTime );

        assertThat( result ).isEqualTo( "Sat, 1 Jan 2000 00:00:00 GMT" );
    }

    @Test
    void testFormatHttpDateForInstant() {

        Instant timestamp = ZonedDateTime.of( 2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC ).toInstant();

        String result = HttpUtils.formatHttpDate( timestamp );

        assertThat( result ).isEqualTo( "Sat, 1 Jan 2000 00:00:00 GMT" );
    }

    @Test
    void testFormatHttpDate() {

        ZonedDateTime dateTime = ZonedDateTime.of( 2000, 1, 1, 0, 0, 0, 0, ZoneId.of( "Asia/Tokyo" ) );

        String result = HttpUtils.formatHttpDate( dateTime );

        assertThat( result ).isEqualTo( "Sat, 1 Jan 2000 00:00:00 +0900" );
    }
}