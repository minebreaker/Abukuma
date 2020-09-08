package rip.deadcode.abukuma3.router.internal;

import com.google.common.base.Splitter;

import static rip.deadcode.abukuma3.internal.utils.MoreCollections.last;


public final class RoutingUtils {

    private RoutingUtils() {
        throw new Error();
    }

    public static final Splitter pathSplitter = Splitter.on( "/" ).omitEmptyStrings().trimResults();

    private static final Splitter extensionSplitter = Splitter.on( "." ).omitEmptyStrings();

    public static String getExtension(String fileName) {
        return last( extensionSplitter.split( fileName ) ).toLowerCase();
    }
}
