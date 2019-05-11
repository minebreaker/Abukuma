package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.collect.Streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;


public class GenericLens<S, A> implements Lens<S, A> {

    private final String path;

    public GenericLens( String path ) {
        this.path = path;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public A get( S object ) {

        if ( object == null ) {
            return null;
        }

        if ( object instanceof Map ) {
            return (A) ( (Map<?, ?>) object ).get( path );
        }

        if ( object instanceof Iterable ) {
            int index = Integer.parseInt( path );
            return (A) Streams.stream( (Iterable<?>) object ).skip( index ).findFirst().get();  // respects ordering
        }

        return null;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public S set( S object, A value ) {

        if ( object == null ) {
            return null;
        }

        if ( object instanceof Map ) {
            Map<Object, Object> map = new HashMap<>( (Map<?, ?>) object );
            map.put( path, value );
            return (S) map;
        }

        if ( object instanceof List ) {
            int index = Integer.parseInt( path );
            List<Object> list = (List) object;
            checkState( list.size() > index );
            List<Object> dest = new ArrayList<>();
            dest.addAll( list.subList( 0, index ) );
            dest.add( value );
            dest.addAll( list.subList( index, list.size() ) );
            return (S) dest;
        }

        return null;
    }
}
