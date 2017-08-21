public class Server {

    public static void main(String[] args) {

        Abukuma().config(config())
                 .route(route())
                 .run();

    }

    private static Config config() {
        return Config.port(8080)
                     .contextPath("/server")
                     .build();

        // From XML
        return Config.loadXml(Paths.get("/foo"));
        // From bean
        return Config.fromBean(bean());
    }

    private static Router route() {
        return Router.route("/", ctx -> "<h1>hello, world</h1>")
                     .context("/api",
                              Router.route("/foo/{id[^[a-z]$]}", fooService())
                                    .route("/bar", Responses.redirect("/")))
                     .route("/user/{id}/[.*]", userService())
                     .route(AntRouter.route("/admin/*"))
                     .notFound(Responses.resource("404.html"));

        // Map directory as route
        return Router.route(DirectoryRouter.base("/var/www/html"));
        // Map resource URL as route
        return Router.route(ResourceRouter.base("public"));
    }

    private static Router rxJava() {
        // static API
        RxAbu.route(Router.route("/rx")).getObservable()
             .subscribe(ctx -> "hello, world");
        return RxAbu.router();

        // DI pattern
        RxAbu rx = RxAbu.newInstance();
        DiContainer.register(rx);

        rx.getObservable()
          .subscribe(ctx -> "hello, world");
        return Router.route("/rx", rx);
    }

}
