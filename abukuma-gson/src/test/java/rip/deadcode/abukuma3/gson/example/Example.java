package rip.deadcode.abukuma3.gson.example;

import rip.deadcode.abukuma3.gson.JsonBody;


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

//        Abukuma.create()
//               .filter( createList( Filters.antiCsrf() ) )
//               .module( createList( GsonModule.getInstance() ) )
//               .router( Routers.create()
//                               .post( "/post", ( ctx, req ) -> {
//                                   Request request = req.body( Request.class );
//                                   Response response = new Response();
//                                   response.setMessage( String.format(
//                                           "hello, %s!", firstNonNull( request.getName(), "world" ) ) );
//                                   return Responses.create( response )
//                                                   .header( h -> h.contentType( "application/json" ) );
//                               } )
//                               .createRouter() )
//               .run();
    }
}
