package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.Unsafe;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;

import java.net.URI;
import java.util.Optional;


public interface Request {

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

    public RequestHeader header();

    public Optional<String> pathParam( String key );

    public PersistentMap<String, String> pathParams();

    public Optional<String> queryParam( String key );

    public PersistentMultimap<String, String> queryParams();

    public Optional<String> host();

    public String remoteAddress();

    @Unsafe
    public Object rawRequest();

    @Unsafe
    public Object rawResponse();
}
