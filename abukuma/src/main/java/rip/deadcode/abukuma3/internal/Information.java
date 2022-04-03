package rip.deadcode.abukuma3.internal;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


public final class Information {

    private static final class BuildInfoReader {

        private String version;
        private String commit;

        private static Optional<String> getResource( String name ) {
            try {
                return Optional.of(
                        Resources.toString( Resources.getResource( name ), StandardCharsets.UTF_8 ).trim()
                );
            } catch ( IOException | IllegalArgumentException e ) {
                return Optional.empty();
            }
        }

        private BuildInfoReader() {
            this.version = getResource( "abukuma-version" ).orElse( "unknown" );
            this.commit = getResource( "abukuma-commit" ).orElse( "unknown" );
        }
    }

    private static final BuildInfoReader buildInfo = new BuildInfoReader();
    public static final String VERSION = buildInfo.version;
    public static final String COMMIT = buildInfo.commit;

    /**
     * <pre>
     * {@code
     * ,_.        ,_.
     * \o/.______.\o/
     *  /( /  J \/\^\
     * //\/ \_/  \ /\\
     * || I  _  I \//|
     *  V`--------'V/
     * }
     * </pre>
     */
    public static final String AA =
            ",_.        ,_.\n" +
            "\\o/.______.\\o/\n" +
            " /( /  J \\/\\^\\\n" +
            "//\\/ \\_/  \\ /\\\\\n" +
            "|| I  _  I \\//|\n" +
            " V`--------'V/";

    private static final char ESC = 27;
    private static final String ANSI_YELLOW = ESC + "[33;m";
    private static final String ANSI_RESET = ESC + "[0m";

    public static final String INFO_STRING =
            "\n\n" + ANSI_YELLOW + Information.AA + ANSI_RESET + "\n" +
            "Aubukuma Web Server " + Information.VERSION + " " + "\n" +
            "Build " + Information.COMMIT + "\n" +
            "https://github.com/minebreaker/Abukuma" + "\n";
}
