package rip.deadcode.abukuma3.gson.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.gson.AbuGson;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.router.AbuRouters;
import rip.deadcode.abukuma3.value.AbuConfigs;
import rip.deadcode.abukuma3.value.AbuResponse;


public final class Example {

    @JsonBody
    private static final class Request {

        private String name;

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }
    }

    @JsonBody
    private static final class Response {

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage( String message ) {
            this.message = message;
        }
    }

    public static void main( String[] args ) {

        Abukuma.config( AbuConfigs.create() )
               .addParser( AbuGson.parser() )
               .addRenderer( AbuGson.renderer() )
               .router( AbuRouters.builder()
                                  .post( "/post", req -> {
                                      Request request = req.body( Request.class );
                                      Response response = new Response();
                                      response.setMessage( String.format(
                                              "hello, %s!", request.getName() != null ? request.getName() : "world" ) );
                                      return AbuResponse.create( response )
                                                        .header( h -> h.contentType( "application/json" ) );
                                  } )
                                  .build() )
               .build()
               .run();
    }
}
