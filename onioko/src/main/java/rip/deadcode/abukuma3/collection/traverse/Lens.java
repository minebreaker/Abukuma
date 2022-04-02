package rip.deadcode.abukuma3.collection.traverse;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;


public interface Lens<S, A> {

    public static <S, A> Lens<S, A> create( Getter<S, A> getter, Setter<S, A> setter ) {

        if ( getter instanceof GetterStreamable ) {
            GetterStreamable<S, A> g = (GetterStreamable<S, A>) getter;
            return new Lens<>() {

                @Override public Getter<S, A> getter() {
                    return g;
                }

                @Override public Stream<A> getAll( S object ) {
                    return g.stream( object );
                }

                @Override public Setter<S, A> setter() {
                    return setter;
                }
            };
        }

        return new Lens<>() {
            @Override public Getter<S, A> getter() {
                return getter;
            }

            @Override public Setter<S, A> setter() {
                return setter;
            }
        };
    }

    public Getter<S, A> getter();

    @Nullable
    public default A get( S object ) {
        return getter().get( object );
    }

    public default Optional<A> mayGet( S object ) {
        return Optional.ofNullable( get( object ) );
    }

    public default Stream<A> getAll( S object ) {
        return mayGet( object ).map( Stream::of ).orElseGet( Stream::empty );
    }

    public Setter<S, A> setter();

    public default S set( S object, A value ) {
        return setter().set( object, value );
    }

    public default S modify( S object, Function<A, A> f ) {
        return set( object, f.apply( get( object ) ) );
    }

    public default <B> Lens<S, B> compose( Lens<A, B> another ) {
        Lens<S, A> self = this;
        return new Lens<>() {

            @Override public Getter<S, B> getter() {
                return object -> another.get( self.get( object ) );
            }

            @Override public Setter<S, B> setter() {
                return ( object, value ) -> self.set( object, another.set( self.get( object ), value ) );
            }
        };
    }
}
