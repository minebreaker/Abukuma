package rip.deadcode.abukuma3.utils.internal;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;
import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.utils.MimeDetector;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static com.google.common.truth.Truth.assertThat;


class DefaultMimeDetectorTest {

    @Test
    void test() {

        MimeDetector detector = new DefaultMimeDetector();

        String result = detector.detect( "/foo/bar/buz.html" );
        assertThat( result ).isEqualTo( "text/html" );

        result = detector.detect( "/foo/bar/buz.css" );
        assertThat( result ).isEqualTo( "text/css" );

        result = detector.detect( "/foo/bar/buz.txt" );
        assertThat( result ).isEqualTo( "text/plain" );

        result = detector.detect( "/foo/bar/buz.js" );
        assertThat( result ).isEqualTo( "application/javascript" );

        result = detector.detect( "/foo/bar/buz.json" );
        assertThat( result ).isEqualTo( "application/json" );

        result = detector.detect( "/foo/bar/buz.xml" );
        assertThat( result ).isEqualTo( "application/xml" );

        result = detector.detect( "/foo/bar/buz.unknown" );
        assertThat( result ).isEqualTo( "*/*" );
    }

    @Test
    void testWithPath() throws IOException {

        FileSystem fs = Jimfs.newFileSystem( Configuration.unix() );
        Path target = fs.getPath( "/foo/bar/buz" );

        Files.createDirectories( target.getParent() );
        Files.write(
                target,
                PersistentCollections.<String>createList()
                        .addFirst( "<html><body><p>hello, world</p></body></html>" ),
                StandardOpenOption.CREATE
        );

        String result = new DefaultMimeDetector().detect( target.toString(), target );

        // Looks like JDK default is not so reliable
        assertThat( result ).isEqualTo( "*/*" );
    }
}
