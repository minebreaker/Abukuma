package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.service.ContentService;
import rip.deadcode.abukuma3.service.Context;
import rip.deadcode.abukuma3.service.FileContentService;

import java.nio.file.Path;

public final class FileServingRouter implements Router, ContentService<Path> {

    private final FileRouter router;

    public FileServingRouter(Path path) {
        router = new FileRouter(path, new FileContentService());
    }

    @Override
    public RoutingResult proceed(Context context) {
        return router.proceed(context);
    }

    @Override
    public RoutingResult feed(Context context, Path target) {
        return router.feed(context, target);
    }
}
