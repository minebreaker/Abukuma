package rip.deadcode.abukuma3.collection.traverse.internal;

import rip.deadcode.abukuma3.collection.traverse.Getter;
import rip.deadcode.abukuma3.collection.traverse.Lens;
import rip.deadcode.abukuma3.collection.traverse.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;


public class PathedMapLens<K, V> implements Lens<Map<K, V>, V> {

    private String path;

    public PathedMapLens( String path ) {
        this.path = path;
    }

    @Override public Getter<Map<K, V>, V> getter() {
        return map -> {
            Optional<K> key = map.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            return key.map( map::get ).orElse( null );
        };
    }

    @Override public Setter<Map<K, V>, V> setter() {
        return ( map, value ) -> {
            Optional<K> key = map.keySet().stream().filter( e -> Objects.equals( e.toString(), path ) ).findAny();
            checkState( key.isPresent() );

            Map<K, V> temp = new HashMap<>( map );
            temp.put( key.get(), value );
            return temp;
        };
    }
}
