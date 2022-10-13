package rip.deadcode.abukuma3.handler;

import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.Response;


@FunctionalInterface
public interface BodilessHandleFunction {
    public Response handle( ExecutionContext context, Request<? extends Request.Empty> request);
}
