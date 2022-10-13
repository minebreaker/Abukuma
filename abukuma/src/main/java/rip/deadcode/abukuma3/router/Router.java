package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.Handler;
import rip.deadcode.abukuma3.router.internal.NotFoundRouter;

import javax.annotation.Nullable;


public interface Router {

    @Nullable
    public RoutingResult route( RoutingContext context );

    @Nullable
    public Handler<?> notFound();

    public default Router ifNotMatch( Router downstream ) {
        Router self = this;
        return new Router() {
            @Nullable @Override public RoutingResult route( RoutingContext context ) {
                RoutingResult result = self.route( context );
                return result != null ? result : downstream.route( context );
            }

            @Nullable @Override public Handler<?> notFound() {
                return downstream.notFound() != null
                       ? downstream.notFound()
                       : self.notFound();
            }
        };
    }

    public default Router notFound( Handler<?> handler ) {
        return this.ifNotMatch( new NotFoundRouter( handler ) );
    }
}
