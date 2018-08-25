package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.config.AbuConfig;
import rip.deadcode.abukuma3.response.AbuResponse;

public final class Example {

    public static void main( String[] args ) throws Exception {

        Abukuma.config( new AbuConfig.Builder().build() )
               .router( _req -> request -> {
                   request.getServletResponse().setContentType( "text/html; charset=utf-8" );
                   String body = request.getBody( String.class );
                   return new AbuResponse( String.format( "<h1>hello, %s</h1>", body.isEmpty() ? "world" : body ) );
               } )
               .build()
               .run();
    }
}
