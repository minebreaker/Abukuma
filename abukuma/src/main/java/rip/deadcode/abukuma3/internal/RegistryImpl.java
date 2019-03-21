package rip.deadcode.abukuma3.internal;

import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.Registry;

import java.util.Map;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.internal.utils.MoreCollections.assoc;


public final class RegistryImpl implements Registry {

    private Map<Class<?>, Map<String, Supplier<?>>> holder;

    public RegistryImpl() {
        holder = ImmutableMap.of();
    }

    private RegistryImpl( Map<Class<?>, Map<String, Supplier<?>>> holder ) {
        this.holder = holder;
    }

    @Override public <T> T get( Class<T> cls ) {
        return get( cls, "" );
    }

    @SuppressWarnings( "unchecked" )
    @Override public <T> T get( Class<T> cls, String name ) {
        return (T) checkNotNull( holder.get( cls ).get( "" ).get() );
    }

    @Override public <T> Registry setSingleton( Class<T> cls, T instance ) {
        return set( cls, "", () -> instance );
    }

    @Override public <T> Registry set( Class<T> cls, Supplier<? extends T> supplier ) {
        return set( cls, "", supplier );
    }

    @Override public <T> Registry set( Class<T> cls, String name, Supplier<? extends T> supplier ) {
        Map<String, Supplier<?>> current = holder.get( cls );
        Map<String, Supplier<?>> newInstanceMap =
                current == null
                ? ImmutableMap.of( name, supplier )
                : assoc( current, name, supplier );
        return new RegistryImpl( assoc( holder, cls, newInstanceMap ) );
    }
}
