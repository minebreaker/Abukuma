package rip.deadcode.abukuma3.multipart.example;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.multipart.AbuFileItemIteratorParser;
import rip.deadcode.abukuma3.router.Routers;
import rip.deadcode.abukuma3.value.Configs;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;
import rip.deadcode.abukuma3.value.Responses;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createList;
import static rip.deadcode.abukuma3.internal.utils.IoStreams.is2str;


public class Example {

    private static final Logger logger = LoggerFactory.getLogger( Example.class );

    public static void main( String[] args ) {

        Abukuma.create()
               .config( Configs.create() )
               .parser( createList( new AbuFileItemIteratorParser() ) )
               .router( Routers.create()
                               .resource( "/", "rip/deadcode/abukuma3/multipart/example/form.html" )
                               .post( "/post", Example::handle )
                               .createRouter() )
               .createServer()
               .run();
    }

    private static Response handle( ExecutionContext context, Request request ) {
        try {
            FileItemIterator iter = request.body( FileItemIterator.class );
            while ( iter.hasNext() ) {
                FileItemStream s = iter.next();
                logger.info( "name: {}, content: {}", s.getName(), is2str( s.openStream() ) );
            }

            return Responses.create( "<p>uploaded</p>" );

        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }
}
