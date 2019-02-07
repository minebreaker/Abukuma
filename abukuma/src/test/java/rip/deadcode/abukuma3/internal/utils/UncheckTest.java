package rip.deadcode.abukuma3.internal.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.izvestia.Core.expect;

final class UncheckTest {

    @Test
    void testTryUncheck1() {
        Exception expected = new IOException( "test" );
        expect( () -> {
            Uncheck.uncheck( () -> {throw expected;} );
        } ).throwsException( e -> {
            assertThat( e ).isInstanceOf( RuntimeException.class );
            assertThat( e ).hasCauseThat().isEqualTo( expected );
        } );

        String s = Uncheck.uncheck( () -> "successful" );
        assertThat( s ).isEqualTo( "successful" );
    }

    @Test
    void testTryUncheck2() {
        IOException expected = new IOException( "test" );
        expect( () -> {
             Uncheck.uncheck( () -> {throw expected;}, UncheckedIOException::new );
        } ).throwsException( e -> {
            assertThat( e ).isInstanceOf( UncheckedIOException.class );
            assertThat( e ).hasCauseThat().isEqualTo( expected );
        } );
    }

}
