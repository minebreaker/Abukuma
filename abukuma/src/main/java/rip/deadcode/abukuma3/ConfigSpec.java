package rip.deadcode.abukuma3;


public interface ConfigSpec {

    public ServerSpec useDefault();

    public ServerSpec useConfig( com.typesafe.config.Config config );

    public ServerSpec useConfigWithDefault( com.typesafe.config.Config config );
}
