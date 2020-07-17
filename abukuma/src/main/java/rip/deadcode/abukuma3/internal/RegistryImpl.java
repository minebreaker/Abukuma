package rip.deadcode.abukuma3.internal;

import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.collection.PersistentCollections;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.assoc;


public final class RegistryImpl implements Registry {

    private Map<Class<?>, Map<String, Function<Registry, ?>>> holder;

    public RegistryImpl() {
        holder = PersistentCollections.createMap();
    }

    private RegistryImpl( Map<Class<?>, Map<String, Function<Registry, ?>>> holder ) {
        this.holder = holder;
    }

    @Override public <T> T get( Class<T> cls ) {
        return get( cls, "" );
    }

    @SuppressWarnings( "unchecked" )
    @Override public <T> T get( Class<T> cls, String name ) {
        return (T) checkNotNull( holder.get( cls ).get( "" ).apply( this ) );
    }

    @Override public <T> Registry setSingleton( Class<T> cls, T instance ) {
        return set( cls, "", () -> instance );
    }

    @Override public <T> Registry set( Class<T> cls, Supplier<? extends T> supplier ) {
        return set( cls, "", supplier );
    }

    @Override public <T> Registry set( Class<T> cls, Function<Registry, ? extends T> generator ) {
        return set( cls, "", generator );
    }

    @Override public <T> Registry set( Class<T> cls, String name, Supplier<? extends T> supplier ) {
        return set( cls, name, registry -> supplier.get() );
    }

    @Override public <T> Registry set( Class<T> cls, String name, Function<Registry, ? extends T> generator ) {
        Map<String, Function<Registry, ?>> current = holder.get( cls );
        Map<String, Function<Registry, ?>> newInstanceMap =
                current == null
                ? ImmutableMap.of( name, generator )
                : assoc( current, name, generator );
        return new RegistryImpl( assoc( holder, cls, newInstanceMap ) );
    }
}
