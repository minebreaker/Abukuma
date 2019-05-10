package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.parser.UrlEncoded;
import rip.deadcode.abukuma3.router.AbuRouters;
import rip.deadcode.abukuma3.value.AbuConfigs;
import rip.deadcode.abukuma3.value.AbuResponses;

import java.nio.file.Paths;


public final class Main {

    public static void main( String[] args ) {

        Abukuma.config( AbuConfigs.create() )
               .router( AbuRouters.create()
                                  .get( "/", req -> AbuResponses.create( "<h1>hello, world</h1>" )
                                                                .header( h -> h.contentType( "text/html" ) ) )
                                  .get( "/user/:name", req -> AbuResponses.create(
                                          String.format( "<h1>hello, %s!</h1>", req.pathParams().get( "name" ) ) )
                                                                          .header( h -> h.contentType( "text/html" ) )
                                  )
                                  .post( "/post", req -> AbuResponses.create(
                                          String.format(
                                                  "<h1>hello, %s!</h1>",
                                                  req.body( UrlEncoded.class ).getValue( "name" )
                                          ) ) )
                                  .context( "context", r -> r
                                          .path( "GET", "/nest", req ->
                                                  AbuResponses.create( "<h1>context</h1>" )
                                                              .header( h -> h.contentType( "text/html" ) ) ) )
                                  .file( "/file", Paths.get(
                                          "./abukuma-example/src/main/resources/rip/deadcode/abukuma3/example/index.html" ) )
                                  .dir( "/dir", Paths.get(
                                          "./abukuma-example/src/main/resources/rip/deadcode/abukuma3/example" ) )
                                  .resource( "/resource", "rip/deadcode/abukuma3/example/index.html" )
                                  .resources( "/resources", "rip/deadcode/abukuma3/example" )
                                  .notFound( req -> AbuResponses.create( "<h1>not found</h1>" )
                                                                .header( h -> h.contentType( "text/html" ) ) ) )
               .build()
               .run();
    }
}
