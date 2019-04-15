package rip.deadcode.abukuma3.multipart.example;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.multipart.AbuFileItemIteratorParser;
import rip.deadcode.abukuma3.router.AbuRouters;
import rip.deadcode.abukuma3.value.AbuConfigs;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuResponse;
import rip.deadcode.abukuma3.value.AbuResponses;

import static rip.deadcode.abukuma3.internal.utils.IoStreams.is2str;


public class Example {

    private static final Logger logger = LoggerFactory.getLogger( Example.class );

    public static void main( String[] args ) {

        Abukuma.config( AbuConfigs.create() )
               .addParser( new AbuFileItemIteratorParser() )
               .router( AbuRouters.builder()
                                  .resource( "/", "rip/deadcode/abukuma3/multipart/example/form.html" )
                                  .post( "/post", Example::handle )
                                  .build() )
               .build()
               .run();
    }

    private static AbuResponse handle( AbuRequest request ) {
        try {
            FileItemIterator iter = request.body( FileItemIterator.class );
            while ( iter.hasNext() ) {
                FileItemStream s = iter.next();
                logger.info( "name: {}, content: {}", s.getName(), is2str( s.openStream() ) );
            }

            return AbuResponses.create( "<p>uploaded</p>" );

        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }
}
