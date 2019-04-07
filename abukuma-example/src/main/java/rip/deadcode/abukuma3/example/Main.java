package rip.deadcode.abukuma3.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.router.AbuRouters;
import rip.deadcode.abukuma3.value.AbuConfigs;
import rip.deadcode.abukuma3.value.AbuResponse;


public final class Main {

    public static void main( String[] args ) {

        Abukuma.config( AbuConfigs.create() )
               .router( AbuRouters.builder()
                                  .get( "/", req -> AbuResponse.create( "<h1>hello, world</h1>" )
                                                               .header( h -> h.contentType( "text/html" ) ) )
                                  .notFound( req -> AbuResponse.create( "<h1>not found</h1>" )
                                                               .header( h -> h.contentType( "text/html" ) ) )
                                  .build() )
               .build()
               .run();
    }
}
