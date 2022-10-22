package rip.deadcode.abukuma3.gson.internal;

public final class GsonConfig {

    private final boolean requireJsonMimeType;
    private final boolean requireAnnotation;

    public GsonConfig( boolean requireJsonMimeType, boolean requireAnnotation ) {
        this.requireJsonMimeType = requireJsonMimeType;
        this.requireAnnotation = requireAnnotation;
    }

    public boolean requireJsonMimeType() {
        return requireJsonMimeType;
    }

    public boolean requireAnnotation() {
        return requireAnnotation;
    }
}
