package rip.deadcode.abukuma3.internal.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static rip.deadcode.abukuma3.internal.utils.Try.except;
import static rip.deadcode.abukuma3.internal.utils.Try.possibly;
import static rip.deadcode.izvestia.Core.expect;


class TryTest {

    private String success() {
        return "test";
    }

    private String fail() throws IOException {
        throw new IOException( "Checked" );
    }

    @Test
    void testOf() {
        assertThat( Try.of( "test" ).get() ).isEqualTo( "test" );
    }

    @Test
    void testExcept() {
        RuntimeException e = new RuntimeException();
        assertThat( except( e ).orElse( ex -> ex ) ).isSameInstanceAs( e );
    }

    @Test
    void testPossibly1() {
        RuntimeException e = new RuntimeException( "" );

        Try<RuntimeException, RuntimeException> result = possibly( () -> {
            throw e;
        }, RuntimeException.class );

        assertThat( result.orElse( ex -> ex ) ).isSameInstanceAs( e );
    }

    @Test
    void testPossibly2() {
        RuntimeException e = new RuntimeException();
        expect( () -> {
            possibly( () -> {
                throw e;
            }, UncheckedIOException.class ).get();
        } ).throwsException( ex -> {
            assertThat( ex ).hasCauseThat().isSameInstanceAs( e );
        } );
    }

    @Test
    void testGet1() {
        String result = possibly( this::success ).get();
        assertThat( result ).isEqualTo( "test" );
    }

    @Test
    void testGet2() {
        expect( () -> {
            possibly( this::fail ).get();
        } ).throwsException( t -> {
            assertThat( t ).isInstanceOf( NoSuchElementException.class );
            assertThat( t ).hasMessageThat().isEqualTo( "Failed Try" );
        } );
    }

    @Test
    void testOrElse1() {
        String result = possibly( this::success ).orElse( "failed" );
        assertThat( result ).isEqualTo( "test" );
    }

    @Test
    void testOrElse2() {
        String result = possibly( this::fail ).orElse( "failed" );
        assertThat( result ).isEqualTo( "failed" );
    }

    @Test
    void testOrElse3() {
        String result = possibly( this::success ).orElse( e -> "failed" );
        assertThat( result ).isEqualTo( "test" );
    }

    @Test
    void testOrElse4() {
        String result = possibly( this::fail ).orElse( e -> {
            assertThat( e ).isInstanceOf( IOException.class );
            return "failed";
        } );
        assertThat( result ).isEqualTo( "failed" );
    }

    @Test
    void testOrElse5() {
        String result = possibly( this::fail, IOException.class ).orElse( ( IOException e ) -> {
            assertThat( e ).isInstanceOf( IOException.class );
            return "failed";
        } );
        assertThat( result ).isEqualTo( "failed" );
    }

    @Test
    void testIsPresent1() {
        boolean result = possibly( this::success ).isPresent();
        assertThat( result ).isTrue();
    }

    @Test
    void testIsPresent2() {
        boolean result = possibly( this::fail ).isPresent();
        assertThat( result ).isFalse();
    }

    private static final class Hook {
        private boolean hook = false;
    }

    @Test
    void testIfPresent1() {
        Hook h = new Hook();
        possibly( this::success ).ifPresent( e -> h.hook = true );
        assertThat( h.hook ).isTrue();
    }

    @Test
    void testIfPresent2() {
        Hook h = new Hook();
        possibly( this::fail ).ifPresent( e -> h.hook = true );
        assertThat( h.hook ).isFalse();
    }

    @Test
    void testMap1() {
        String result = possibly( this::success ).map( String::toUpperCase ).orElse( "failed" );
        assertThat( result ).isEqualTo( "TEST" );
    }

    @Test
    void testMap2() {
        String result = possibly( this::fail ).map( String::toUpperCase ).orElse( "failed" );
        assertThat( result ).isEqualTo( "failed" );
    }

    @Test
    void testFlatMap1() {
        Try<String, Exception> m = Try.<String, IllegalArgumentException>possibly( () -> "test2" );
        String result = possibly( this::success ).flatMap( e -> m ).get();
        assertThat( result ).isEqualTo( "test2" );
    }

    @Test
    void testFlatMap2() {
        Try<String, Exception> m = Try.<String, IllegalArgumentException>possibly( () -> "test2" );
        String result = possibly( this::fail ).flatMap( e -> m ).orElse( "failed" );
        assertThat( result ).isEqualTo( "failed" );
    }

    @Test
    void testAsOptional1() {
        Optional<String> result = Try.of( "" ).asOptional();
        assertThat( result.isPresent() ).isTrue();
    }

    @Test
    void testAsOptional2() {
        Optional<String> result = Try.<String, RuntimeException>except( new RuntimeException() ).asOptional();
        assertThat( result.isPresent() ).isFalse();
    }
}