package rip.deadcode.abukuma3.collection.traverse;

import com.google.common.base.Splitter;
import rip.deadcode.abukuma3.collection.traverse.internal.GenericLens;

import java.util.List;


// TODO
public final class Optics {

    private static final Splitter s = Splitter.on( "/" ).omitEmptyStrings().trimResults();

    @SuppressWarnings( "unchecked" )
    public static <S, A> Lens<S, A> path( String path ) {

        List<String> segments = s.splitToList( path );

        return (Lens<S, A>) segments.stream()
                                    .map( segment -> (Lens<Object, Object>) new GenericLens<>( segment ) )
                                    .reduce( Lens::compose )
                                    .get();
    }

    @SuppressWarnings( "unchecked" )
    public static <S, A> Lens<S, A> path( Object... pathSegments ) {
        throw new UnsupportedOperationException();
    }
}
