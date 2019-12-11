package rip.deadcode.abukuma3.utils;


import javax.annotation.Nullable;
import java.nio.file.Path;


@FunctionalInterface
public interface MimeDetector {

    /**
     * @param pathAsString A file path to detect mime type.
     * @param path         A {@link Path} object of the {@literal pathAsString}.
     *                     When specified, the first argument {@literal pathAsString} must be
     *                     equal to the value of {@code path.toString()}.
     * @return A possible mime type string of the given file.
     */
    public String detectMime( String pathAsString, @Nullable Path path );
}
