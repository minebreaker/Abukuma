package rip.deadcode.abukuma3.gson.example;

import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.gson.JsonBody;
import rip.deadcode.abukuma3.router.StandardRouters;
import rip.deadcode.abukuma3.value.Responses;

import java.util.Optional;

import static rip.deadcode.abukuma3.handler.Handlers.createHandler;


public final class Example {

    @JsonBody
    private static final class SampleRequestBody {

        private String name;

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }
    }

    @JsonBody
    private static final class SampleResponse {

        private String message;

        private SampleResponse( String message ) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage( String message ) {
            this.message = message;
        }
    }

    public static void main( String[] args ) {

        Abukuma.create()
               .router( StandardRouters.path(
                       "POST",
                       "/hello",
                       createHandler( ( SampleRequestBody.class ), ( request -> {
                           SampleRequestBody body = request.body();
                           var name = Optional.ofNullable( body.getName() ).orElse( "world" );
                           var response = new SampleResponse( String.format(
                                   "hello, %s",
                                   name
                           ) );
                           return Responses.create( response )
                                           .header( h -> h.contentType( "application/json" ) );
                       } ) )
               ) )
               .run();
    }
}
