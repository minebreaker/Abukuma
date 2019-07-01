package rip.deadcode.abukuma3.collection.traverse.internal;


import rip.deadcode.abukuma3.collection.AbuPersistentList;
import rip.deadcode.abukuma3.collection.PersistentListMultimap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;


public final class PathedPersistentListMultimapLens<K, V, T extends PersistentListMultimap<K, V, T>>
        implements Lens<T, List<V>> {

    private String path;

    public PathedPersistentListMultimapLens( String path ) {
        this.path = path;
    }

    @Override public Getter<T, List<V>> getter() {
        return multimap -> {
            Optional<K> key = multimap.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            return key.isPresent() ? multimap.get( key.get() ) : AbuPersistentList.create();
        };
    }

    @Override public Setter<T, List<V>> setter() {
        return ( multimap, values ) -> {
            Optional<K> key = multimap.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            checkState( key.isPresent() );

            return multimap.set( key.get(), values );
        };
    }
}
