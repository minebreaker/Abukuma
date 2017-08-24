package rip.deadcode.abukuma3.route;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.service.Context;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ContextualRouter implements Router {

    private static final String SLASH = "/";
    private final Splitter splitter = Splitter.on(SLASH).omitEmptyStrings();
    private final Joiner joiner = Joiner.on(SLASH);

    private final String contextPath;
    private final Router child;

    private final boolean tryMatchingEverytime;

    public ContextualRouter(String context, Router child) {
        checkNotNull(context);
        checkNotNull(child);

        this.contextPath = regularize(context);
        this.child = child;
        this.tryMatchingEverytime = false;
    }

    public ContextualRouter(String context, Router child, boolean tryMatchingEverytime) {
        this.contextPath = regularize(context);
        this.child = child;
        this.tryMatchingEverytime = tryMatchingEverytime;
    }

    @Override
    public RoutingResult proceed(Context context) {

        if (!tryMatchingEverytime && context.getContextualPath().startsWith(contextPath)) {
            // Not in context
            return RoutingResult.notMatched();
        }

        // Remove contextual path
        String contextRemoved = context.getContextualPath().replaceFirst(contextPath, "");
        String newContext = contextRemoved.startsWith("/")
                            ? contextRemoved
                            : "/" + contextRemoved;

        return child.proceed(context.contextualPath(newContext));
    }

    private String regularize(String original) {

        // "foo/bar/" -> "/foo/bar"

        List<String> parts = splitter.splitToList(original);

        StringBuilder ret = new StringBuilder()
                .append(SLASH);
        return joiner.appendTo(ret, parts).toString();
    }

}
