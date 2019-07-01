package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;


public final class PathedPersistentMapLens<K, V, T extends PersistentMap<K, V, T>>
        implements Lens<T, V> {

    private String path;

    public PathedPersistentMapLens( String path ) {
        this.path = path;
    }

    @Override public Getter<T, V> getter() {
        return map -> {
            Optional<K> key = map.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            return key.map( map::get ).orElse( null );
        };
    }

    @Override public Setter<T, V> setter() {
        return ( map, value ) -> {
            Optional<K> key = map.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            checkState( key.isPresent() );

            return map.set( key.get(), value );
        };
    }
}
