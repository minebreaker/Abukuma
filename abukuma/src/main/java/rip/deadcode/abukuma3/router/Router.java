package rip.deadcode.abukuma3.router;

import rip.deadcode.abukuma3.handler.Handler;

import javax.annotation.Nullable;


public interface Router {

    @Nullable
    public RoutingResult route( RoutingContext context );

    @Nullable
    public Handler notFound();

    public default Router ifNotMatch( Router downstream ) {
        Router self = this;
        return new Router() {
            @Nullable @Override public RoutingResult route( RoutingContext context ) {
                RoutingResult result = self.route( context );
                return result != null ? result : downstream.route( context );
            }

            @Nullable @Override public Handler notFound() {
                return downstream.notFound() != null
                       ? downstream.notFound()
                       : self.notFound();
            }
        };
    }

    // FIXME 合成のやり方を改善する
    public default Router notFound( Handler handler ) {
        Router self = this;
        return new Router() {
            @Nullable @Override public RoutingResult route( RoutingContext context ) {
                return self.route( context );
            }

            @Override public Handler notFound() {
                return handler;
            }
        };
    }
}
