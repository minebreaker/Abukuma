package rip.deadcode.abukuma3.service;

import com.twitter.finagle.http.Response;
import com.twitter.finagle.http.Status;
import com.twitter.util.Future;
import rip.deadcode.abukuma3.route.RoutingResult;
import rip.deadcode.abukuma3.route.RoutingResultImpl;
import rip.deadcode.abukuma3.service.contenttype.ContentTypeResolver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileContentService implements ContentService<Path> {

    private ContentTypeResolver resolver = ContentTypeResolver.getInstance();

    @Override
    public RoutingResult feed(Context context, Path target) {

        if (Files.notExists(target)) {
            return RoutingResult.notMatched();
        }

        // TODO 別スレッド
        // TODO キャッシュ
        String content;
        try {
            content = new String(Files.readAllBytes(target), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Response response = Response.apply(Status.Ok());
        response.setContentType(resolver.getContentType(target.getFileName().toString()), "UTF-8");
        // TODO ストリームはどうすんだ
        response.setContentString(content);
        return new RoutingResultImpl(context.response(Future.value(response)));
    }

}
