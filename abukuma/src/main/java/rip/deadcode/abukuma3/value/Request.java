package rip.deadcode.abukuma3.value;

import rip.deadcode.abukuma3.Unsafe;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;

import java.net.URI;
import java.util.Optional;


public interface Request<T> {

    public interface Empty {
        public static final Empty singleton = new Empty() {};
    }

    public T body();

    public RequestHeader header();

    public Optional<String> pathParam( String key );

    public PersistentMap<String, String> pathParams();

    public Optional<String> queryParam( String key );

    public PersistentMultimap<String, String> queryParams();

    public default String method() {
        return header().method();
    }

    public default URI url() {
        return header().url();
    }

    public default String urlString() {
        return header().urlString();
    }

    @Unsafe
    public Object rawRequest();

    @Unsafe
    public Object rawResponse();
}
