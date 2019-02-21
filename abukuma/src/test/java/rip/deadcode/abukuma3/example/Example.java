package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.parser.UrlEncoded;
import rip.deadcode.abukuma3.router.AbuRouters;
import rip.deadcode.abukuma3.value.AbuConfig;
import rip.deadcode.abukuma3.value.AbuResponse;


public final class Example {

    public static void main( String[] args ) {

        Abukuma.config( AbuConfig.create() )
               .router( AbuRouters.builder()
                                  .get( "/", req -> AbuResponse.create( "<h1>hello, world</h1>" )
                                                               .header( h -> h.contentType( "text/html" ) ) )
                                  .get( "/user/:name", req -> AbuResponse.create(
                                          String.format( "<h1>hello, %s!</h1>", req.pathParams().get( "name" ) ) )
                                                                         .header( h -> h.contentType( "text/html" ) )
                                  )
                                  .resource( "/resource", "rip/deadcode/abukuma3/example/post.html" )
                                  .resources( "/resources", "rip/deadcode/abukuma3/example" )
                                  .post( "/post", req -> AbuResponse.create(
                                          String.format(
                                                  "<h1>hello, %s!</h1>",
                                                  req.body( UrlEncoded.class ).getValue( "name" )
                                          ) ) )
                                  .notFound( req -> AbuResponse.create( "<h1>not found</h1>" )
                                                               .header( h -> h.contentType( "text/html" ) ) )
                                  .build() )
               .build()
               .run();
    }
}
