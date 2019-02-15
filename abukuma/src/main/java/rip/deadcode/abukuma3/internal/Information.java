package rip.deadcode.abukuma3.internal;

public final class Information {

    public static final int MAJOR = 0;
    public static final int MINOR = 1;
    public static final int PATCH = 0;

    public static final String NOTE = "Experimental";

    public static final String VERSION = "Abukuma " + MAJOR + "." + MINOR + "." + PATCH + " (" + NOTE + ")";


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
    private static final String ANSI_REST = ESC + "[0m";

    public static final String INFO_STRING = "\n\n" + ANSI_YELLOW + Information.AA + ANSI_REST + "  " + Information.VERSION + "\n";
}
