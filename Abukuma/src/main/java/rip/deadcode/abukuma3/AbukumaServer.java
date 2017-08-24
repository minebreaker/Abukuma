package rip.deadcode.abukuma3;

import com.twitter.finagle.Http;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;
import com.twitter.util.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rip.deadcode.abukuma3.config.Config;
import rip.deadcode.abukuma3.route.ContextualRouter;
import rip.deadcode.abukuma3.route.Router;
import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.ContextImpl;

final class AbukumaServer {

    private static final Logger logger = LoggerFactory.getLogger(AbukumaServer.class);

    private final Config config;
    private final Router router;
    private final Service<Request, Response> service;

    AbukumaServer(Config config, Router router) {
        this.config = config;
        this.router = new ContextualRouter(config.getContextPath(), router, true);
        this.config.validate();

        Router lexicalRouter = this.router;
        service = new Service<Request, Response>() {
            @Override
            public Future<Response> apply(Request request) {
                Context context = new ContextImpl(request)
                        .contextualPath(request.path());
                return lexicalRouter.proceed(context)
                                    .result()
                                    .orElseThrow(() -> new RuntimeException("Could not find matching router: " + request.path()))
                                    .getResponse();
            }
        };

    }

    void run() throws TimeoutException, InterruptedException {
        ListeningServer server = Http.server().serve(config.getAddress(), service);
        logger.info("Abukuma starts to listening...");  // Does it work correctly??
        Await.ready(server);
    }

}
