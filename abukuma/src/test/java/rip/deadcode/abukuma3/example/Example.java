package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.config.AbuConfig;
import rip.deadcode.abukuma3.response.AbuResponse;
import rip.deadcode.abukuma3.router.AbuRouters;

public final class Example {

    public static void main( String[] args ) throws Exception {

//        Abukuma.config( new AbuConfig.Builder().build() )
//               .router( _req -> request -> {
//                   String body = request.getBody( String.class );
//                   request.getServletResponse().setContentType( "text/html; charset=utf-8" );
//                   return new AbuResponse( String.format( "<h1>hello, %s</h1>", body.isEmpty() ? "world" : body ) );
//               } )
//               .build()
//               .run();

        Abukuma.config( new AbuConfig.Builder()
                                .port( 8080 )
                                .build() )
               .router( AbuRouters.builder()
                                  .get( "/", req -> new AbuResponse( "hello, root" ) )
                                  .get( "/get", req -> new AbuResponse( "hello, get" ) )
                                  .get( "/param/:name", req -> new AbuResponse(
                                          String.format( "hello, %s!", req.getPathParams().get( "name" ) ) )
                                  )
                                  .post( "/post", req -> new AbuResponse( "hello, post" ) )
                                  .notFound( req -> new AbuResponse( "not found" ) )
                                  .build() )
               .build()
               .run();
    }
}
