package rip.deadcode.abukuma3;


public interface PluginConfigSpec<T> {

    /**
     * The path of the configuration.
     * If the value is {@code foo}, {@code abukuma.foo} is used for the
     * path to resolve the {@link com.typesafe.config.Config} object.
     */
    public String configPath();

    public Class<T> configClass();

    /**
     * This method defines how to covert typesafe {@code Config} into
     * the plugin specific config object.
     * The resulting object will be available via {@link Registry}.
     */
    public T resolve( com.typesafe.config.Config typesafeConfig );
}
