package rip.deadcode.abukuma3;


public interface Plugin<T> {

    public PluginConfigSpec<T> configSpec();

    public Module module();
}
