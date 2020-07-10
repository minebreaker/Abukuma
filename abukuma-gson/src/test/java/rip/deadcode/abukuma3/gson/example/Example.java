package rip.deadcode.abukuma3.gson.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.filter.Filters;
import rip.deadcode.abukuma3.gson.GsonModule;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.router.Routers;
import rip.deadcode.abukuma3.value.Configs;
import rip.deadcode.abukuma3.value.Responses;

import static com.google.common.base.MoreObjects.firstNonNull;


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

        Abukuma.config( Configs.create() )
               .addFilter( Filters.antiCsrf() )
               .addModule( GsonModule.getInstance() )
               .router( Routers.create()
                               .post( "/post", ( ctx, req ) -> {
                                      Request request = req.body( Request.class );
                                      Response response = new Response();
                                      response.setMessage( String.format(
                                              "hello, %s!", firstNonNull( request.getName(), "world" ) ) );
                                      return Responses.create( response )
                                                      .header( h -> h.contentType( "application/json" ) );
                                  } ) )
               .build()
               .run();
    }
}
