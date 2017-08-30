package rip.deadcode.abukuma3.route;

import rip.deadcode.abukuma3.service.ContentService;
import rip.deadcode.abukuma3.service.Context;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkState;

public final class FileRouter implements Router, ContentService<Path> {

    private Path baseDir;
    private final ContentService<Path> service;

    public FileRouter(Path baseDir, ContentService<Path> service) {
        this.baseDir = baseDir.normalize().toAbsolutePath();
        this.service = service;
    }

    @Override
    public RoutingResult proceed(Context context) {

        String requestedPath = context.getContextualPath();
        Path target = baseDir.resolve(requestedPath).normalize().toAbsolutePath();
        checkState(target.startsWith(baseDir), "Directory traversal detected.");
        return feed(context, target);
    }

    @Override
    public RoutingResult feed(Context context, Path target) {
        return service.feed(context, target);
    }

}
