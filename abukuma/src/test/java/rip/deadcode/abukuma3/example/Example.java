package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.AbuConfig;
import rip.deadcode.abukuma3.AbuResponse;
import rip.deadcode.abukuma3.Abukuma;

public final class Example {

    public static void main( String[] args ) throws Exception {

        Abukuma.config( new AbuConfig() )
               .router( _req -> request -> {
                   request.getServletResponse().setContentType( "text/html; charset=utf-8" );
                   return new AbuResponse( "<h1>hello, world</h1>" );
               } )
               .build()
               .run();
    }
}
