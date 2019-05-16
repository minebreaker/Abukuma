package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import rip.deadcode.abukuma3.value.AbuConfig;

import javax.annotation.Nullable;
import java.util.*;


public final class AbuConfigImpl implements AbuConfig, Map<String, Object> {

    private int port;
    private int maxThreads;
    private int minThreads;
    @Nullable
    private String serverImplementation;

    public AbuConfigImpl() {
        this.port = 8080;
        this.maxThreads = 128;
        this.minThreads = 8;
    }

    public AbuConfigImpl( int port, int maxThreads, int minThreads, @Nullable String serverImplementation ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
        this.serverImplementation = serverImplementation;
    }

    private AbuConfigImpl copy() {
        return new AbuConfigImpl( port, maxThreads, minThreads, serverImplementation );
    }

    @Override
    public Optional<String> serverImplementation() {
        return Optional.ofNullable( serverImplementation );
    }

    @Override
    public AbuConfigImpl serverImplementation( String serverImplementation ) {
        AbuConfigImpl c = copy();
        c.serverImplementation = serverImplementation;
        return c;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public AbuConfigImpl port(int port) {
        AbuConfigImpl c = copy();
        c.port = port;
        return c;
    }

    @Override
    public int maxThreads() {
        return maxThreads;
    }

    @Override
    public AbuConfigImpl maxThreads(int maxThreads) {
        AbuConfigImpl c = copy();
        c.maxThreads = maxThreads;
        return c;
    }

    @Override
    public int minThreads() {
        return minThreads;
    }

    @Override
    public AbuConfigImpl minThreads(int minThreads) {
        AbuConfigImpl c = copy();
        c.minThreads = minThreads;
        return c;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
//        return Objects.equals(key, "port") || Objects.equals(key, "maxThreads") || Objects.equals(key, "minThreads");
        switch (key.toString()) {
            case "port":
            case "maxThreads":
            case "minThreads":
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        return Objects.equals(port, value) || Objects.equals(maxThreads, value) || Objects.equals(minThreads, value);
    }

    @Override
    public Object get(Object key) {
        switch (key.toString()) {
            case "port":
                return port;
            case "maxThreads":
                return maxThreads;
            case "minThreads":
                return minThreads;
            default:
                return null;
        }
    }

    private static final Set<String> keySet = ImmutableSet.of("port, maxThreads", "minThreads");

    @Override
    public Set<String> keySet() {
        return keySet;
    }

    @Override
    public Collection<Object> values() {
        return ImmutableSet.of(port, maxThreads, minThreads);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return ImmutableSet.of(
                Maps.immutableEntry("port", port),
                Maps.immutableEntry("maxThreads", maxThreads),
                Maps.immutableEntry("minThreads", minThreads)
        );
    }

    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
