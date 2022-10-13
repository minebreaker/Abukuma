package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;

import java.util.function.Function;


public final class Handlers {

    private Handlers() {
        throw new Error();
    }

    public static Handler<Void> createHandler( BodilessHandleFunction handler ) {
        return new Handler<>() {

            @Override public Class<Void> bodyType() {
                return Void.class;
            }

            @Override public Response handle( ExecutionContext context, Request<? extends Void> request ) {
                return handler.handle( context, request );
            }
        };
    }

    public static <T> Handler<T> createHandler( Class<T> cls, Function<Request<? extends T>, Response> handler ) {
        return new Handler<T>() {
            @Override public Class<T> bodyType() {
                return cls;
            }

            @Override public Response handle( ExecutionContext context, Request<? extends T> request ) {
                return handler.apply( request );
            }
        };
    }
}
