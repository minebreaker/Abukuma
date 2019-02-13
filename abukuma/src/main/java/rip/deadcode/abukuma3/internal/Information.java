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
     * \o/,------.\o/
     *  // /   |\/\^\
     * //\/ \_/  \ /\\
     * ||  I    I \//|
     * \|\___~____/ V/
     * }
     * </pre>
     */
    public static final String AA =
            ",_.        ,_.\n"
            + "\\o/,------.\\o/\n"
            + " // /   |\\/\\^\\\n"
            + "//\\/ \\_/  \\ /\\\\\n"
            + "||  I    I \\//|\n"
            + "\\|\\___~____/ V/";


    private static final char ESC = 27;
    private static final String ANSI_YELLOW = ESC + "[33;m";
    private static final String ANSI_REST = ESC + "[0m";

    public static final String INFO_STRING = "\n\n" + ANSI_YELLOW + Information.AA + ANSI_REST + "  " + Information.VERSION + "\n";
}
