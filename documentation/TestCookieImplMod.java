package rip.deadcode.abukuma3.value.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import rip.deadcode.abukuma3.collection.AbuPersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMap;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;


public final class TestCookieImplMod implements rip.deadcode.abukuma3.value.TestCookie,
        PersistentMap<String, Object, AbuPersistentMap<String, Object>> {


    public TestCookieImplMod(
            String name, String value
    ) {
        this.name = checkNotNull( name );
        this.value = checkNotNull( value );
    }

    private TestCookieImplMod copy() {
        return new TestCookieImplMod( name, value );
    }


    private String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public TestCookieImplMod name(
            String name
    ) {
        checkNotNull( name );
        TestCookieImplMod copy = copy();
        copy.name = name;
        return copy;
    }

    private String value;

    @Override
    public String value() {
        return value;
    }

    @Override
    public TestCookieImplMod value(
            String value
    ) {
        checkNotNull( value );
        TestCookieImplMod copy = copy();
        copy.value = value;
        return copy;
    }


    @Override
    public int size() {
        return 2;  // number of properties
    }

    @Override
    public boolean isEmpty() {
        return false;  // always false (even if all properties are null, considers it has a key of the property with null value)
    }

    @Override
    public boolean containsKey( Object key ) {
        return Objects.equals( key, "name" )
                || Objects.equals( key, "value" );
    }

    @Override
    public boolean containsValue( Object value ) {
        return Objects.equals( value, name )
                || Objects.equals( value, this.value );
    }

    @Nullable
    @Override
    public Object get( Object key ) {
        if ( Objects.equals( key, "name" ) ) {
            return name;
        } else if ( Objects.equals( key, "value" ) ) {
            return value;
        } else {
            return null;
        }
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

    @Override
    public Set<String> keySet() {
        return ImmutableSet.of( "name", "value" );
    }

    @Override
    public Collection<Object> values() {
        return ImmutableList.of( name, value );
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return ImmutableSet.of(
                Maps.immutableEntry( "name", name ),
                Maps.immutableEntry( "value", value )
        );
    }

    @Override
    public boolean equals( Object o ) {
//        return o instanceof Map
//                && this.entrySet().equals( ((Map) o).entrySet() );
        return o instanceof Map
                && ( (Map) o ).size() == 2
                && Objects.equals( name, ( (Map) o ).get( "name" ) )
                && Objects.equals( value, ( (Map) o ).get( "value" ) );
    }

    @Override
    public int hashCode() {
        return this.entrySet().hashCode();
    }


    @Override
    public Optional<Object> mayGet( String key ) {
        if ( Objects.equals( key, "name" ) ) {
            return Optional.of( name );
        } else if ( Objects.equals( key, "value" ) ) {
            return Optional.of( value );
        } else {
            return Optional.empty();
        }
    }

    @Override
    public AbuPersistentMap<String, Object> set( String key, Object value ) {
        checkNotNull( key );
        checkNotNull( value );

        return AbuPersistentMap.<String, Object>create().set( key, value );
    }

    @Override
    public AbuPersistentMap<String, Object> delete( String key ) {
        if ( containsKey( key ) ) {
            return AbuPersistentMap.<String, Object>create()
//                    .merge( this )
.delete( key );
        } else {
            throw new NoSuchElementException( key );
        }
    }
}
