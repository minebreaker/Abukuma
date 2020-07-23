package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.value.UrlEncoded;
import rip.deadcode.abukuma3.router.Routers;
import rip.deadcode.abukuma3.value.Configs;
import rip.deadcode.abukuma3.value.Responses;

import java.nio.file.Paths;


public final class Main {

    public static void main( String[] args ) {

        Abukuma.config( Configs.create() )
               .router( Routers.create()
                               .get( "/", ( ctx, req ) -> Responses.create( "<h1>hello, world</h1>" )
                                                                   .header( h -> h.contentType( "text/html" ) ) )
                               .get( "/user/:name", ( ctx, req ) -> Responses.create(
                                       String.format( "<h1>hello, %s!</h1>", req.pathParams().get( "name" ) ) )
                                                                             .header( h -> h.contentType(
                                                                                     "text/html" ) ) )
                               .post( "/post", ( ctx, req ) -> Responses.create(
                                       String.format(
                                               "<h1>hello, %s!</h1>",
                                               req.body( UrlEncoded.class ).getValue( "name" )
                                       ) ) )
                               .context( "context", r -> r
                                       .path( "GET", "/nest", ( ctx, req ) ->
                                               Responses.create( "<h1>context</h1>" )
                                                        .header( h -> h.contentType( "text/html" ) ) ) )
                               .file( "/file", Paths.get(
                                       "./abukuma-example/src/main/resources/rip/deadcode/abukuma3/example/index.html" ) )
                               .dir( "/dir", Paths.get(
                                       "./abukuma-example/src/main/resources/rip/deadcode/abukuma3/example" ) )
                               .resource( "/resource", "rip/deadcode/abukuma3/example/index.html" )
                               .resources( "/resources", "rip/deadcode/abukuma3/example" )
                               .notFound( ( ctx, req ) -> Responses.create( "<h1>not found</h1>" )
                                                                   .header( h -> h.contentType( "text/html" ) ) ) )
               .build()
               .run();
    }
}
