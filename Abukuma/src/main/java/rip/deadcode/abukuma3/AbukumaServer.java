package rip.deadcode.abukuma3;

import com.twitter.finagle.Http;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;
import com.twitter.util.TimeoutException;
import rip.deadcode.abukuma3.config.Config;
import rip.deadcode.abukuma3.route.Router;

final class AbukumaServer {

    private final Config config;
    private final Router router;
    private final Service<Request, Response> service;

    AbukumaServer(Config config, Router router) {
        this.config = config;
        this.router = router;

        service = new Service<Request, Response>() {
            @Override
            public Future<Response> apply(Request request) {
                return router.proceed(null)
                             .result()
                             .orElseThrow(() -> new RuntimeException("Could not find matching router"))
                             .getResponse();
            }
        };

    }

    void run() throws TimeoutException, InterruptedException {
        ListeningServer server = Http.server().serve(config.getAddress(), service);
        Await.ready(server);
    }

}
