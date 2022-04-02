package rip.deadcode.abukuma3.utils.url;


import java.net.URI;

import static rip.deadcode.abukuma3.internal.utils.Uncheck.uncheck;


/**
 * Serialize given {@link UrlModel} to the URL {@link String}.
 */
@FunctionalInterface
public interface UrlRenderer {

    public String render(UrlModel model);

    public default URI renderAsJava(UrlModel model) {
        return uncheck(() -> new URI( render( model ) ));
    }
}
