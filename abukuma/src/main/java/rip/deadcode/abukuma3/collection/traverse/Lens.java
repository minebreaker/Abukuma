package rip.deadcode.abukuma3.collection.traverse;

import java.util.function.BiFunction;
import java.util.function.Function;


public interface Lens<S, A> {

    public static <S, A> Lens<S, A> create( Function<S, A> getter, BiFunction<S, A, S> setter ) {

        return new Lens<S, A>() {
            @Override
            public A get( S object ) {
                return getter.apply( object );
            }

            @Override
            public S set( S object, A value ) {
                return setter.apply( object, value );
            }
        };
    }

    public A get( S object );

    public S set( S object, A value );

    public default S modify( S object, Function<A, A> f ) {
        return set( object, f.apply( get( object ) ) );
    }

    public default <B> Lens<S, B> compose( Lens<A, B> another ) {
        Lens<S, A> self = this;
        return new Lens<S, B>() {
            @Override
            public B get( S object ) {
                return another.get( self.get( object ) );
            }

            @Override
            public S set( S object, B value ) {
                return self.set( object, another.set( self.get( object ), value ) );
            }
        };
    }
}
