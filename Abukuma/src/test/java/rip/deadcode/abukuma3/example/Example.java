package rip.deadcode.abukuma3.example;

import com.twitter.finagle.http.Response;
import com.twitter.finagle.http.Status;
import com.twitter.util.Future;
import rip.deadcode.abukuma3.Abukuma;
import rip.deadcode.abukuma3.config.Config;
import rip.deadcode.abukuma3.config.MutableConfig;
import rip.deadcode.abukuma3.route.AntRouter;
import rip.deadcode.abukuma3.route.Router;
import rip.deadcode.abukuma3.route.SimpleStuipdRouter;
import rip.deadcode.abukuma3.service.Context;

import java.net.InetSocketAddress;

public final class Example {

    public static void main(String[] args) {
        Abukuma.config(config())
               .route(route())
               .run();
    }

    private static Config config() {
        MutableConfig config = new MutableConfig();
        config.setAddress(new InetSocketAddress(8080));
        return config;
    }

    private static Router route() {
        return Router.builder()
                     .route(new SimpleStuipdRouter("/hello", Example::service))
                     .route(new AntRouter("/hello/{name:[a-zA-Z]*}", Example::greetingService))
                     .notFound(Example::service404)
                     .build();
    }

    private static Context service(Context context) {
        Response response = Response.apply(Status.Ok());
        response.setContentString("<h1>hello, world</h1>");
        response.setContentType("text/html", "UTF-8");
        return context.response(Future.value(response));
    }

    private static Context greetingService(Context context) {
        Response response = Response.apply(Status.Ok());
        response.setContentString("<h1>hello, " + context.getPathParam().get("name") + "</h1>");
        response.setContentType("text/html", "UTF-8");
        return context.response(Future.value(response));
    }

    private static Context service404(Context context) {
        Response response = Response.apply(Status.NotFound());
        response.setContentString("<h2>Not found</h2><a href=\"/hello\">To greeting page</a>");
        response.setContentType("text/html", "UTF-8");
        return context.response(Future.value(response));
    }

}
