package rip.deadcode.abukuma3.internal;

import rip.deadcode.abukuma3.Registry;
import rip.deadcode.abukuma3.collection.PersistentMap;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static rip.deadcode.abukuma3.collection.PersistentCollections.createMap;


public final class RegistryImpl implements Registry {

    private final PersistentMap<Class<?>, PersistentMap<String, Function<Registry, ?>>> holder;

    public RegistryImpl() {
        holder = createMap();
    }

    private RegistryImpl( PersistentMap<Class<?>, PersistentMap<String, Function<Registry, ?>>> holder ) {
        this.holder = holder;
    }

    @Override public <T> T get( Class<T> cls ) {
        return get( cls, "" );
    }

    @SuppressWarnings( "unchecked" )
    @Override public <T> T get( Class<T> cls, String name ) {
        return (T) checkNotNull( holder.get( cls ).get( name ).apply( this ) );
    }

    @Override public <T> Optional<T> mayGet( Class<T> cls ) {
        return mayGet( cls, "" );
    }

    @SuppressWarnings( "unchecked" )
    @Override public <T> Optional<T> mayGet( Class<T> cls, String name ) {
        return holder.mayGet( cls )
                     .flatMap( c -> c.mayGet( name ) )
                     .map( c -> (T) c.apply( this ) );
    }

    @Override public <T> Registry setSingleton( Class<T> cls, T instance ) {
        return set( cls, "", () -> instance );
    }

    @Override public Registry unsafeSetSingleton( Class<?> cls, Object instance ) {
        var current = holder.mayGet( cls ).orElse( createMap() );
        return new RegistryImpl(
                holder.set( cls, current.set( "", registry -> instance ) )
        );
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
        // TODO
        PersistentMap<String, Function<Registry, ?>> current = holder.mayGet( cls ).orElse( createMap() );
        return new RegistryImpl( holder.set( cls, current.set( name, generator ) ) );
    }
}
