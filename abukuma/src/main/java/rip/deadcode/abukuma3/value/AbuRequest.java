package rip.deadcode.abukuma3.value;

import com.google.common.collect.Multimap;
import rip.deadcode.abukuma3.Unsafe;

import java.net.URI;
import java.util.Map;
import java.util.Optional;


public interface AbuRequest {

    public <T> T body( Class<T> cls );

    public String method();

    /**
     * A convenience method for {@code header().url()}.
     */
    public URI url();

    /**
     * A convenience method for {@code header().urlString()}
     */
    public String urlString();

    public AbuRequestHeader header();

    public Optional<String> pathParam( String key );

    public Map<String, String> pathParams();

    public Optional<String> queryParam( String key );

    public Multimap<String, String> queryParams();

    public Optional<String> host();

    public String remoteAddress();

    @Unsafe
    public Object rawRequest();

    @Unsafe
    public Object rawResponse();
}
