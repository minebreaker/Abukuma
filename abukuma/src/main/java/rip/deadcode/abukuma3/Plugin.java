package rip.deadcode.abukuma3;


public interface Plugin {

    public PluginConfigSpec<?> configSpec();

    public Module module();
}
