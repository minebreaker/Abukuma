package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.Maps;
import rip.deadcode.abukuma3.collection.PersistentSet;
import rip.deadcode.abukuma3.value.Config;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static rip.deadcode.abukuma3.collection.PersistentCollections.createSet;


public final class ConfigImpl implements Config, Map<String, Object> {

    private int port;
    private int maxThreads;
    private int minThreads;
    @Nullable
    private String serverImplementation;
    private boolean ssl;

    public ConfigImpl() {
        this.port = 8080;
        this.maxThreads = 128;
        this.minThreads = 8;
        this.ssl = false;
    }

    public ConfigImpl( int port, int maxThreads, int minThreads, @Nullable String serverImplementation, boolean ssl ) {
        this.port = port;
        this.maxThreads = maxThreads;
        this.minThreads = minThreads;
        this.serverImplementation = serverImplementation;
        this.ssl = ssl;
    }

    private ConfigImpl copy() {
        return new ConfigImpl( port, maxThreads, minThreads, serverImplementation, ssl );
    }

    @Override
    public Optional<String> serverImplementation() {
        return Optional.ofNullable( serverImplementation );
    }

    @Override
    public ConfigImpl serverImplementation( String serverImplementation ) {
        ConfigImpl c = copy();
        c.serverImplementation = serverImplementation;
        return c;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public ConfigImpl port( int port ) {
        ConfigImpl c = copy();
        c.port = port;
        return c;
    }

    @Override
    public int maxThreads() {
        return maxThreads;
    }

    @Override
    public ConfigImpl maxThreads( int maxThreads ) {
        ConfigImpl c = copy();
        c.maxThreads = maxThreads;
        return c;
    }

    @Override
    public int minThreads() {
        return minThreads;
    }

    @Override
    public ConfigImpl minThreads( int minThreads ) {
        ConfigImpl c = copy();
        c.minThreads = minThreads;
        return c;
    }

    @Override public boolean ssl() {
        return ssl;
    }

    @Override public ConfigImpl ssl( boolean ssl ) {
        ConfigImpl c = copy();
        c.ssl = ssl;
        return c;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey( Object key ) {
//        return Objects.equals(key, "port") || Objects.equals(key, "maxThreads") || Objects.equals(key, "minThreads");
        switch ( key.toString() ) {
        case "port":
        case "maxThreads":
        case "minThreads":
        case "ssl":
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean containsValue( Object value ) {
        return Objects.equals( port, value ) || Objects.equals( maxThreads, value ) ||
               Objects.equals( minThreads, value );
    }

    @Override
    public Object get( Object key ) {
        switch ( key.toString() ) {
        case "port":
            return port;
        case "maxThreads":
            return maxThreads;
        case "minThreads":
            return minThreads;
        case "ssl":
            return ssl;
        default:
            return null;
        }
    }

    private static final PersistentSet<String> keySet = createSet( "port, maxThreads", "minThreads" );

    @Override
    public PersistentSet<String> keySet() {
        return keySet;
    }

    @Override
    public Collection<Object> values() {
        return createSet( port, maxThreads, minThreads );
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return createSet(
                Maps.immutableEntry( "port", port ),
                Maps.immutableEntry( "maxThreads", maxThreads ),
                Maps.immutableEntry( "minThreads", minThreads ),
                Maps.immutableEntry( "ssl", ssl )
        );
    }

    @Override
    public Object put( String key, Object value ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove( Object key ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll( Map<? extends String, ?> m ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
