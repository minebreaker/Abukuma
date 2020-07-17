package rip.deadcode.abukuma3.collection.traverse.internal;


import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;


public final class PathedPersistentListMultimapLens<K, V>
        implements Lens<PersistentMultimap<K, V>, List<V>> {

    private final String path;

    public PathedPersistentListMultimapLens( String path ) {
        this.path = path;
    }

    @Override public Getter<PersistentMultimap<K, V>, List<V>> getter() {
        return multimap -> {
            Optional<K> key = multimap.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            return key.isPresent() ? multimap.get( key.get() ) : PersistentCollections.createList();
        };
    }

    @Override public Setter<PersistentMultimap<K, V>, List<V>> setter() {
        return ( multimap, values ) -> {
            Optional<K> key = multimap.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            checkState( key.isPresent() );

            return multimap.set( key.get(), values );
        };
    }
}
